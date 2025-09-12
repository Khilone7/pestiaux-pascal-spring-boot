package com.safetynet.api.service;

import com.safetynet.api.controller.dto.ListPersonAndStationDto;
import com.safetynet.api.controller.dto.PersonAndMedicationDto;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FireService {

    private final PersonRepository personRepository;
    private final FireStationRepository fireStationRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;

    public ListPersonAndStationDto getPersonAndStationByAddress(String address) {
        Optional<Long> stationNumber = getStationNumberByAddress(address);
        List<Person> personList = getPersonByAddress(address);

        Map<String, MedicalRecord> medicalRecordMap = medicalRecordsRepository.getAllMedicalRecord()
                .stream()
                .collect(Collectors.toMap(m -> m.getFirstName() + m.getLastName(), m -> m));

        List<PersonAndMedicationDto> personDtoList = personList
                .stream()
                .map(p -> { MedicalRecord m = medicalRecordMap.get(p.getFirstName()+p.getLastName());
                return buildPersonDto(p,m);})
                .toList();
        return new ListPersonAndStationDto(personDtoList,stationNumber);
    }

    public PersonAndMedicationDto buildPersonDto(Person person, MedicalRecord medicalRecord) {
        int age = Period.between(medicalRecord.getBirthdate(), LocalDate.now()).getYears();
        return new PersonAndMedicationDto(person.getLastName(), person.getPhone(), age, medicalRecord.getMedications(), medicalRecord.getAllergies());
    }

    public Optional<Long> getStationNumberByAddress(String address) {
        return fireStationRepository.getAllFireStation()
                .stream()
                .filter(f -> f.getAddress().equals(address))
                .map(FireStation::getStation)
                .findFirst();

    }

    public List<Person> getPersonByAddress(String address) {
        return personRepository.getAllPerson()
                .stream()
                .filter(p -> address.equals(p.getAddress()))
                .toList();
    }

}