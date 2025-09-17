package com.safetynet.api.service;

import com.safetynet.api.controller.dto.AdultDto;
import com.safetynet.api.controller.dto.ChildAlertResponseDto;
import com.safetynet.api.controller.dto.ChildDto;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChildAlertServiceTest {

    @Mock
    MedicalRecordsRepository medicalRecordsRepository;
    @Mock
    PersonRepository personRepository;
    @InjectMocks
    ChildAlertService childAlertService;

    static Person buildPerson(String firstName,String lastName,String address){
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setAddress(address);
        return person;
    }

    static MedicalRecord buildMr(String firstName,String lastName,LocalDate birthdate){
        MedicalRecord mr = new MedicalRecord();
        mr.setFirstName(firstName);
        mr.setLastName(lastName);
        mr.setBirthdate(birthdate);
        return mr;
    }

    Person child1, child2, adult1, adult2;
    MedicalRecord childMr1, childMr2, adultMr1, adultMr2;

    @BeforeEach
    void setUp() {
        child1 = buildPerson("Jean","Dupont","3 rue du marais");
        childMr1 = buildMr("Jean", "Dupont", LocalDate.now().minusYears(8));
        child2  = buildPerson("Marc",  "Henry",  "2 rue de l'église");
        childMr2 = buildMr("Marc", "Henry", LocalDate.now().minusYears(18));
        adult1  = buildPerson("Tony",  "Durand", "3 rue du marais");
        adultMr1 = buildMr("Tony", "Durand", LocalDate.now().minusYears(50));
        adult2  = buildPerson("Pierre","Niney", "1 rue du stade");
        adultMr2 = buildMr("Pierre","Niney", LocalDate.now().minusYears(19));

        when(personRepository.getAllPerson())
                .thenReturn(List.of(child1, child2, adult1, adult2));
        when(medicalRecordsRepository.getAllMedicalRecord())
                .thenReturn(List.of(childMr1, childMr2, adultMr1, adultMr2));
    }

    @Test
    void getChildByAddressShouldReturnChildAndAdult() {
        ChildAlertResponseDto result = childAlertService.getChildByAddress("3 rue du marais");

        assertThat(result.childList()).hasSize(1);
        assertThat(result.childList().getFirst())
                .extracting(ChildDto::firstName, ChildDto::lastName)
                .containsExactly("Jean", "Dupont");

        assertThat(result.adultList()).hasSize(1);
        assertThat(result.adultList().getFirst())
                .extracting(AdultDto::firstName, AdultDto::lastName)
                .containsExactly("Tony", "Durand");
    }

    @Test
    void getChildByAddressShouldReturnChildOnly() {
        ChildAlertResponseDto result = childAlertService.getChildByAddress("2 rue de l'église");

        assertThat(result.childList()).hasSize(1);
        assertThat(result.childList().getFirst())
                .extracting(ChildDto::firstName, ChildDto::lastName)
                .containsExactly("Marc", "Henry");

        assertTrue(result.adultList().isEmpty());
    }

    @Test
    void getChildByAddressShouldReturnNothingBecauseNoChildIn(){
        ChildAlertResponseDto result = childAlertService.getChildByAddress("1 rue du stade");

        assertTrue(result.childList().isEmpty());
        assertTrue(result.adultList().isEmpty());
    }

    @Test
    void getChildByAddressShouldReturnNothingBecauseWrongAddress(){
        ChildAlertResponseDto result = childAlertService.getChildByAddress("8 rue du marais");

        assertTrue(result.childList().isEmpty());
        assertTrue(result.adultList().isEmpty());
    }
}