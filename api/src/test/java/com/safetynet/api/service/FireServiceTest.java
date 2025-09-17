package com.safetynet.api.service;

import com.safetynet.api.controller.dto.ListPersonAndStationDto;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.FireStationRepository;
import com.safetynet.api.repository.MedicalRecordsRepository;
import com.safetynet.api.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FireServiceTest {

    @Mock
    PersonRepository personRepository;
    @Mock
    MedicalRecordsRepository medicalRecordsRepository;
    @Mock
    FireStationRepository fireStationRepository;
    @InjectMocks
    FireService fireService;

    static Person buildPerson(String firstName, String lastName, String address, String phone) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setAddress(address);
        person.setPhone(phone);
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
    FireStation station1, station2;

    @BeforeEach
    void setUp() {
        person1 = buildPerson("Jean", "Dujardin", "24 rue de l'église", "123");
        personMr1 = buildMr("Jean", "Dujardin", LocalDate.now().minusYears(30), List.of("Doliprane", "Ventoline"), List.of("Chien", "Chat"));
        person2 = buildPerson("Thierry", "Henry", "24 rue de l'église", "707");
        personMr2 = buildMr("Thierry", "Henry", LocalDate.now().minusYears(40), List.of(), List.of());
        person3 = buildPerson("Jamel", "Debouzze", "2 rue de l'église", "789");
        personMr3 = buildMr("Jamel", "Debouzze", LocalDate.now().minusYears(50), List.of(), List.of());

        station1 = FireStation.builder().station(1L).address("24 rue de l'église").build();
        station2 = FireStation.builder().station(2L).address("2 rue de l'église").build();

        when(personRepository.getAllPerson())
                .thenReturn(List.of(person1, person2, person3));
        when(fireStationRepository.getAllFireStation())
                .thenReturn(List.of(station1, station2));
        when(medicalRecordsRepository.getAllMedicalRecord())
                .thenReturn(List.of(personMr1, personMr2, personMr3));
    }

    @Test
    void getTwoPersonAndOneStationByAddress() {
        ListPersonAndStationDto result = fireService.getPersonAndStationByAddress("24 rue de l'église");

        assertThat(result.stationNumber()).contains(1L);
        assertThat(result.listPerson()).hasSize(2);
        assertThat(result.listPerson())
                .extracting("lastName", "phone", "age", "medication", "allergies")
                .containsExactlyInAnyOrder(tuple("Dujardin", "123", 30, List.of("Doliprane", "Ventoline"), List.of("Chien", "Chat")),
                        tuple("Henry", "707", 40, List.of(), List.of()));
    }

    @Test
    void getOnePersonAndOneStationByAddress() {
        ListPersonAndStationDto result = fireService.getPersonAndStationByAddress("2 rue de l'église");

        assertThat(result.stationNumber()).contains(2L);
        assertThat(result.listPerson()).hasSize(1);
        assertThat(result.listPerson())
                .extracting("lastName", "phone", "age", "medication", "allergies")
                .containsExactly(tuple("Debouzze", "789", 50, List.of(), List.of()));
    }

    @Test
    void getNotingWhenWrongAddress() {
        ListPersonAndStationDto result = fireService.getPersonAndStationByAddress("243 rue de l'église");

        assertThat(result.stationNumber()).isEmpty();
        assertThat(result.listPerson()).isEmpty();
    }
}