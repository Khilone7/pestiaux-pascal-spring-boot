package com.safetynet.api.Integration;

import com.safetynet.api.controller.dto.FullNameDto;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PersonServiceIT {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonService personService;

    Person charles, john, lewis;

    @BeforeEach
    void setUp() {
        charles = new Person();
        charles.setFirstName("Charles");
        charles.setLastName("Leclerc");
        charles.setAddress("1 Rue du Circuit");
        charles.setCity("Monaco");
        charles.setZip("98000");
        charles.setPhone("111");
        charles.setEmail("charles.leclerc@example.com");

        john = new Person();
        john.setFirstName("John");
        john.setLastName("Boyd");
        john.setAddress("1509 Culver St");
        john.setCity("Culver");
        john.setZip("97451");
        john.setPhone("841-874-6512");
        john.setEmail("jaboyd@email.com");

        lewis = new Person();
        lewis.setFirstName("Lewis");
        lewis.setLastName("Hamilton");
        lewis.setAddress("44 Silverstone Road");
        lewis.setCity("Stevenage");
        lewis.setZip("SG1 1AA");
        lewis.setPhone("07 98 76 54 32");
        lewis.setEmail("lewis.hamilton@example.com");
    }

    @Test
    void addPersonShouldIncreaseListSize() {
        int sizeBefore = personRepository.getAllPerson().size();

        personService.addPerson(charles);
        assertThat(personRepository.getAllPerson()).hasSize(sizeBefore + 1);
    }

    @Test
    void addExistingPersonShouldThrow() {
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> personService.addPerson(john));
    }

    @Test
    void updatePersonShouldModifyPhone() {
        int sizeBefore = personRepository.getAllPerson().size();
        john.setPhone("777");

        personService.updatePerson(john);
        assertThat(personRepository.getAllPerson()).hasSize(sizeBefore);
        assertTrue(personRepository.getAllPerson()
                .stream()
                .anyMatch(p -> p.getFirstName().equals("John")
                        && p.getLastName().equals("Boyd")
                        && p.getPhone().equals("777")));
    }

    @Test
    void updateNonExistingPersonShouldThrow() {
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> personService.updatePerson(lewis));
    }

    @Test
    void deletePersonShouldDecreaseListSize() {
        int sizeBefore = personRepository.getAllPerson().size();
        FullNameDto peterFullName = new FullNameDto ("Peter", "Duncan");

        personService.deletePerson(peterFullName);
        assertThat(personRepository.getAllPerson()).hasSize(sizeBefore - 1);
    }

    @Test
    void deleteNonExistingPersonShouldThrow() {
        FullNameDto fullName = new FullNameDto ("Zinedine", "Zidane");
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> personService.deletePerson(fullName));
    }
}