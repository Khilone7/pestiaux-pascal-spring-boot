package com.safetynet.api.service;

import com.safetynet.api.controller.dto.ResidentDto;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.FireStationRepository;
import com.safetynet.api.repository.MedicalRecordsRepository;
import com.safetynet.api.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class FloodStationService {

    private final PersonRepository personRepository;
    private final FireStationRepository fireStationRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;

    public Map<String, List<ResidentDto>> getListResidentAndAddressAndMedicationByStation(List<Long> stationsNumbers) {

        List<String> addresseList = getAddressesByStationsNumber(stationsNumbers);
        List<Person> personList = getPersonsByAddresses(addresseList);

        Map<String, MedicalRecord> medicalRecordMap = medicalRecordsRepository.getAllMedicalRecord()
                .stream()
                .collect(Collectors.toMap(m -> m.getFirstName() + m.getLastName(), m -> m));

        return  personList.stream()
                .collect(Collectors.groupingBy(Person::getAddress, Collectors.mapping(p -> {
                    MedicalRecord mr = medicalRecordMap.get(p.getFirstName() + p.getLastName());
                    int age = Period.between(mr.getBirthdate(), LocalDate.now()).getYears();
                    return new ResidentDto(p.getLastName(), p.getPhone(), age, mr.getMedications(), mr.getAllergies());
                }, Collectors.toList())));
    }

    private List<String> getAddressesByStationsNumber(List<Long> stationsNumbers) {
        List<String> addresseList = fireStationRepository.getAllFireStation()
                .stream()
                .filter(f -> stationsNumbers.contains(f.getStation()))
                .map(FireStation::getAddress)
                .toList();
        log.debug("There are {} addresses for {} station(s)", addresseList.size(), stationsNumbers.size());
        return addresseList;
    }

    private List<Person> getPersonsByAddresses(List<String> addresses) {
        List<Person> personList = personRepository.getAllPerson()
                .stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .toList();
        log.debug("There are {} persons across {} addresses", personList.size(), addresses.size());
        return personList;
    }
}