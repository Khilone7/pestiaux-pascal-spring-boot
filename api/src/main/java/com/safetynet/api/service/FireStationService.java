package com.safetynet.api.service;

import com.safetynet.api.controller.dto.PersonDto;
import com.safetynet.api.controller.dto.StationDto;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.FireStationRepository;
import com.safetynet.api.repository.MedicalRecordsRepository;
import com.safetynet.api.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FireStationService {

    private static final Logger logger = LogManager.getLogger(FireStationService.class);

    private final PersonRepository personRepository;
    private final FireStationRepository fireStationRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;

    public StationDto getPersonsByStation(Long station) {

        List<String> address = addressesByStation(station);
        List<Person> listNonFilter = getPersonByAddresses(address);

        Long child = childNumber(listNonFilter);
        Long adult = adultNumber(listNonFilter, child);

        List<PersonDto> listFilter = listNonFilter.stream().map(p -> new PersonDto(p.getFirstName(), p.getLastName(), p.getAddress(), p.getPhone())).toList();

        return new StationDto(listFilter, child, adult);
    }

    private List<String> addressesByStation(Long station) {
        return fireStationRepository.getAllFireStation()
                .stream()
                .filter(f -> station.equals(f.getStation()))
                .map(FireStation::getAddress)
                .toList();
    }

    private List<Person> getPersonByAddresses(List<String> address) {
        return personRepository.getAllPerson()
                .stream()
                .filter(p -> address.contains(p.getAddress()))
                .toList();
    }

    private Long childNumber(List<Person> listPerson) {
        LocalDate today = LocalDate.now();
        Map<String, LocalDate> birthdateMap = medicalRecordsRepository.getAllMedicalRecord()
                .stream()
                .collect(Collectors.toMap(m -> m.getFirstName() + m.getLastName(), MedicalRecord::getBirthdate));

        Long child = listPerson
                .stream()
                .filter(p -> {
                    String personName = p.getFirstName()+p.getLastName();
                    LocalDate birthdate = birthdateMap.get(personName);
                    return calculateAge(birthdate,today) <= 18;
                })
                .count();

        logger.debug("Il y'a {} enfants dan la liste", child);
        return child;
    }


    private Long adultNumber(List<Person> listPerson, Long child) {
        Long adult = listPerson.size() - child;
        logger.debug("Il y'a {} adultes dans la liste", adult);
        return adult;
    }

    private int calculateAge(LocalDate birthdate, LocalDate currentDate) {
        Period period = Period.between(birthdate, currentDate);
        logger.debug("La personne a {} ans", period.getYears());
        return period.getYears();
    }
}
