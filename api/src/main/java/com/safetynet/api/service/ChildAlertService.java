package com.safetynet.api.service;

import com.safetynet.api.controller.dto.AdultDto;
import com.safetynet.api.controller.dto.ChildAlertResponseDto;
import com.safetynet.api.controller.dto.ChildDto;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.MedicalRecordsRepository;
import com.safetynet.api.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChildAlertService {

    private static final Logger logger = LogManager.getLogger(ChildAlertService.class);

    private final MedicalRecordsRepository medicalRecordsRepository;
    private final PersonRepository personRepository;


    public ChildAlertResponseDto getChildByAddress(String address) {
        List<Person> person = getPersonByAddress(address);
        Map<Person, Integer> personAge = getPersonAge(person);

        List<ChildDto> childList = getChildList(personAge);
        if (childList.isEmpty()) {
            return new ChildAlertResponseDto (Collections.emptyList(), Collections.emptyList());
        }
        List<AdultDto> adultList = getAdultList(personAge);

        return new ChildAlertResponseDto(childList, adultList);
    }



    private List<Person> getPersonByAddress(String address) {
        return personRepository.getAllPerson()
                .stream()
                .filter(p -> p.getAddress().equals(address))
                .toList();
    }


    private Map<Person, Integer> getPersonAge(List<Person> personList) {
        Map<String, LocalDate> mapPersonAge = medicalRecordsRepository.getAllMedicalRecord()
                .stream()
                .collect(Collectors.toMap(m -> m.getFirstName() + m.getLastName(), MedicalRecord::getBirthdate));

        return personList
                .stream()
                .collect(Collectors.toMap(p -> p, p -> Period.between(mapPersonAge.get(p.getFirstName() + p.getLastName()), LocalDate.now()).getYears()));
    }

    private List<ChildDto> getChildList(Map<Person, Integer> personIntegerMap) {
        return personIntegerMap.entrySet()
                .stream()
                .filter(p -> p.getValue() <= 18)
                .map(p -> new ChildDto(p.getKey().getFirstName(), p.getKey().getLastName(), p.getValue()))
                .toList();
    }

    private List<AdultDto> getAdultList(Map<Person, Integer> personIntegerMap) {
        return personIntegerMap.entrySet()
                .stream()
                .filter(p -> p.getValue() > 18)
                .map(p -> new AdultDto(p.getKey().getFirstName(), p.getKey().getLastName()))
                .toList();
    }
}