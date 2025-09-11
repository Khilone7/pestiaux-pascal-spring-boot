package com.safetynet.api.service;

import com.safetynet.api.controller.dto.FireDto;
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
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FireService {

    private final PersonRepository personRepository;
    private final FireStationRepository fireStationRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;

    public FireDto.ListPersonAndStationDto getPersonAndStationByAddress(String address) {
        Long stationNumber = getStationNumberByAddress(address);
        List<Person> personList = getPersonByAddress(address);
        Set<String> personName = getFirstAndLastName(personList);
        List<MedicalRecord> personMedicalRecords = getMedicalRecordByPerson(personName);

        List<FireDto.PersonDto> personDtoList = personList.stream().map()

    }

    public Map<String, MedicalRecord> medicalRecordMap() {
        return medicalRecordsRepository.getAllMedicalRecord()
                .stream()
                .collect(Collectors.toMap(m -> m.getFirstName() + m.getLastName(), m -> m));
    }

    public FireDto.PersonDto buildPersonDto(Person person, MedicalRecord medicalRecord) {
        Long age = (long) Period.between(medicalRecord.getBirthdate(), LocalDate.now()).getYears();
        return new FireDto.PersonDto(person.getLastName(), person.getPhone(), age, medicalRecord.getMedications(), medicalRecord.getAllergies());
    }

    public Long getStationNumberByAddress(String address) {
        for (FireStation f : fireStationRepository.getAllFireStation()) {
            if (address.equals(f.getAddress())) {
                return f.getStation();
            }
        }
        return null;
    }

    public List<Person> getPersonByAddress(String address) {
        return personRepository.getAllPerson()
                .stream()
                .filter(p -> address.equals(p.getAddress()))
                .toList();
    }

    public String nameKey(String firstName, String lastName) {
        return firstName + lastName;
    }

    public Set<String> getFirstAndLastName(List<Person> personList) {
        return personList.stream().map(p -> p.getFirstName() + p.getLastName()).collect(Collectors.toSet());
    }

    public List<MedicalRecord> getMedicalRecordByPerson(Set<String> personName) {
        return medicalRecordsRepository.getAllMedicalRecord()
                .stream()
                .filter(m -> personName.contains(m.getFirstName() + m.getLastName()))
                .toList();
    }


}
