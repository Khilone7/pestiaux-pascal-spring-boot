package com.safetynet.api.service;

import com.safetynet.api.controller.dto.StationDto;
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

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FireStationServiceTest {

    @Mock
    PersonRepository personRepository;
    @Mock
    FireStationRepository fireStationRepository;
    @Mock
    MedicalRecordsRepository medicalRecordsRepository;
    @InjectMocks
    FireStationService fireStationService;

    static Person buildPerson(String firstName, String lastName, String address, String phone) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setAddress(address);
        person.setPhone(phone);
        return person;
    }

    static MedicalRecord buildMr(String firstName, String lastName, LocalDate birthdate) {
        MedicalRecord mr = new MedicalRecord();
        mr.setFirstName(firstName);
        mr.setLastName(lastName);
        mr.setBirthdate(birthdate);
        return mr;
    }

    Person person1, person2, person3, person4;
    MedicalRecord personMr1, personMr2, personMr3, personMr4;
    FireStation station1, station2, station3;

    @BeforeEach
    void setUp() {

        station1 = FireStation.builder().station(1L).address("24 rue de l'église").build();
        station2 = FireStation.builder().station(2L).address("2 rue de l'église").build();
        station3 = FireStation.builder().station(1L).address("31 rue du stade").build();
        person1 = buildPerson("Franck", "Dubosc", "24 rue de l'église", "123");
        personMr1 = buildMr("Franck", "Dubosc", LocalDate.now().minusYears(34));
        person2 = buildPerson("Kevin", "Tran", "24 rue de l'église", "456");
        personMr2 = buildMr("Kevin", "Tran", LocalDate.now().minusYears(12));
        person3 = buildPerson("Mireille", "Mathieu", "2 rue de l'église", "707");
        personMr3 = buildMr("Mireille", "Mathieu", LocalDate.now().minusYears(20));
        person4 = buildPerson("Alban", "Ivanov", "31 rue du stade", "999");
        personMr4 = buildMr("Alban", "Ivanov", LocalDate.now().minusYears(8));

        when(personRepository.getAllPerson())
                .thenReturn(List.of(person1, person2, person3, person4));
        when(medicalRecordsRepository.getAllMedicalRecord())
                .thenReturn(List.of(personMr1, personMr2, personMr3, personMr4));
        when(fireStationRepository.getAllFireStation())
                .thenReturn(List.of(station1, station2, station3));

    }

    @Test
    void getPersonsByStation_shouldReturnTwoPersonsAndCountsForStation1() {
        StationDto result = fireStationService.getPersonsByStation(1L);

        assertThat(result.personList()).hasSize(3);

        assertThat(result.personList())
                .extracting("firstName", "lastName", "address", "phone")
                .containsExactlyInAnyOrder(
                        tuple("Franck", "Dubosc", "24 rue de l'église", "123"),
                        tuple("Kevin", "Tran", "24 rue de l'église", "456"),
                        tuple("Alban", "Ivanov", "31 rue du stade", "999"));

        assertThat(result.child()).isEqualTo(2L);
        assertThat(result.adult()).isEqualTo(1L);
    }

    @Test
    void getPersonsByStation_shouldReturnOnePersonAndCountsForStation2() {
        StationDto result = fireStationService.getPersonsByStation(2L);

        assertThat(result.personList()).hasSize(1);

        assertThat(result.personList())
                .extracting("firstName", "lastName", "address", "phone")
                .containsExactly(tuple("Mireille", "Mathieu", "2 rue de l'église", "707"));

        assertThat(result.child()).isZero();
        assertThat(result.adult()).isEqualTo(1L);
    }

    @Test
    void getPersonsByStation_shouldReturnEmptyWhenStationUnknown() {
        StationDto result = fireStationService.getPersonsByStation(12L);

        assertThat(result.personList()).isEmpty();
        assertThat(result.child()).isZero();
        assertThat(result.adult()).isZero();
    }
}
