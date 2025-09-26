package com.safetynet.api.service;

import com.safetynet.api.controller.dto.PersonInfoLastNameDto;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
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

/**
 * Retrieves persons matching a given last name and enriches them with
 * address, age and medical information.
 * <p>
 * Combines data from {@link PersonRepository} and {@link MedicalRecordsRepository}.
 * Age is computed from {@link MedicalRecord#getBirthdate()} relative to the current date.
 * Matching on the last name uses simple string equality (case-sensitive).
 * </p>
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class PersonInfoLastNameService {

    private final PersonRepository personRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;

    /**
     * Returns the list of persons whose last name matches the given value,
     * each enriched with address, age, email and medical details.
     *
     * @param lastName last name to match exactly
     * @return list of {@link PersonInfoLastNameDto} entries
     */
    public List<PersonInfoLastNameDto> getPersonAddressAndMedicationsByName (String lastName){
        List<Person> personList = getPersonListByLastName(lastName);

        Map<String, MedicalRecord> medicalRecordMap = medicalRecordsRepository.getAllMedicalRecord()
                .stream()
                .collect(Collectors.toMap(m -> m.getFirstName()+m.getLastName(),m -> m));

        return personList
                .stream()
                .map(p -> {
                    MedicalRecord mr = medicalRecordMap.get(p.getFirstName() + p.getLastName());
                    int age = Period.between(mr.getBirthdate(), LocalDate.now()).getYears();
                    return new PersonInfoLastNameDto(p.getLastName(),p.getAddress(),age, p.getEmail(),mr.getMedications(),mr.getAllergies());
                }).toList();
    }

    /**
     * Returns all persons whose last name equals the provided value.
     * <p>Comparison is case-sensitive.</p>
     *
     * @param lastName last name to match exactly
     * @return list of matching persons
     */
    private List<Person> getPersonListByLastName(String lastName) {
        List<Person> persons = personRepository.getAllPerson()
                .stream()
                .filter(p -> p.getLastName().equals(lastName))
                .toList();
        log.debug("There are {} persons with last name '{}'", persons.size(), lastName);
        return persons;
    }
}