package com.safetynet.api.service;

import com.safetynet.api.controller.dto.HouseholdDto;
import com.safetynet.api.controller.dto.ResidentDto;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.FireStationRepository;
import com.safetynet.api.repository.MedicalRecordsRepository;
import com.safetynet.api.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FloodStationService {

    private final PersonRepository personRepository;
    private final FireStationRepository fireStationRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;

    public List<HouseholdDto> getListResidentAndMedicationByAddresses(List<Long> stationsNumbers) {

        List<String> addresses = getAddressesByStationsNumber(stationsNumbers);
        List<Person> personList = getPersonsByAddresses(addresses);

        Map<String, MedicalRecord> medicalRecordMap = medicalRecordsRepository.getAllMedicalRecord()
                .stream()
                .collect(Collectors.toMap(m -> m.getFirstName() + m.getLastName(), m -> m));

        return personList.stream()
                .collect(Collectors.groupingBy(Person::getAddress, Collectors.mapping(p -> {
                            MedicalRecord mr = medicalRecordMap.get(p.getFirstName() + p.getLastName());
                            int age = Period.between(mr.getBirthdate(), LocalDate.now()).getYears();
                            return new ResidentDto(p.getLastName(), p.getPhone(), age, mr.getMedications(), mr.getAllergies());
                        }, Collectors.toList())))
                .entrySet().stream()
                .map(e -> new HouseholdDto(e.getKey(), e.getValue()))
                .toList();
    }

    private List<String> getAddressesByStationsNumber(List<Long> stationsNumbers) {
        return fireStationRepository.getAllFireStation()
                .stream()
                .filter(f -> stationsNumbers.contains(f.getStation()))
                .map(FireStation::getAddress)
                .toList();
    }

    private List<Person> getPersonsByAddresses(List<String> addresses) {
        return personRepository.getAllPerson()
                .stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .toList();
    }
}