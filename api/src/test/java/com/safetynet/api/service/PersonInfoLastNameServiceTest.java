package com.safetynet.api.service;

import com.safetynet.api.controller.dto.PersonInfoLastNameDto;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.MedicalRecordsRepository;
import com.safetynet.api.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonInfoLastNameServiceTest {

    @Mock
    PersonRepository personRepository;
    @Mock
    MedicalRecordsRepository medicalRecordsRepository;
    @InjectMocks
    PersonInfoLastNameService personInfoLastNameService;

    static Person buildPerson(String firstName, String lastName, String address, String email) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setAddress(address);
        person.setEmail(email);
        return person;
    }

    static MedicalRecord buildMr(String firstName, String lastName, LocalDate birthdate, List<String> medication, List<String> allergies) {
        MedicalRecord mr = new MedicalRecord();
        mr.setFirstName(firstName);
        mr.setLastName(lastName);
        mr.setBirthdate(birthdate);
        mr.setMedications(medication);
        mr.setAllergies(allergies);
        return mr;
    }

    Person person1, person2, person3;
    MedicalRecord personMr1, personMr2, personMr3;

    @BeforeEach
    void setUp() {
        person1 = buildPerson("Joe", "Dalton", "1 rue du Far West", "joe@dalton.com");
        personMr1 = buildMr("Joe", "Dalton", LocalDate.now().minusYears(35), List.of("Doliprane"), List.of("Chien","Chat"));
        person2 = buildPerson("Averell", "Dalton", "2 rue du Far West", "averell@dalton.com");
        personMr2 = buildMr("Averell", "Dalton", LocalDate.now().minusYears(30), List.of(), List.of());
        person3 = buildPerson("Lucky", "Luke", "3 rue du Far West", "lucky@luke.com");
        personMr3 = buildMr("Lucky", "Luke", LocalDate.now().minusYears(40), List.of(), List.of());

        when(personRepository.getAllPerson())
                .thenReturn(List.of(person1, person2, person3));
        when(medicalRecordsRepository.getAllMedicalRecord())
                .thenReturn(List.of(personMr1, personMr2, personMr3));
    }


    @Test
    void getPersonAddressAndMedicationsByNameShouldReturnAllDaltons() {
        List<PersonInfoLastNameDto> result = personInfoLastNameService.getPersonAddressAndMedicationsByName("Dalton");

        assertThat(result).hasSize(2);
        assertThat(result)
                .extracting("lastName", "address", "age", "email", "medications", "allergies")
                .containsExactlyInAnyOrder(
                        tuple("Dalton", "1 rue du Far West", 35, "joe@dalton.com", List.of("Doliprane"), List.of("Chien","Chat")),
                        tuple("Dalton", "2 rue du Far West", 30, "averell@dalton.com", List.of(), List.of()));
    }

    @Test
    void getPersonAddressAndMedicationsByNameShouldReturnEmptyListWhenLastNameUnknown() {
        List<PersonInfoLastNameDto> result = personInfoLastNameService.getPersonAddressAndMedicationsByName("Luk");

        assertThat(result).isEmpty();
    }
}