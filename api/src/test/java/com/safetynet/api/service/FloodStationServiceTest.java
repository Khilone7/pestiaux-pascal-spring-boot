package com.safetynet.api.service;

import com.safetynet.api.controller.dto.ResidentDto;
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
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FloodStationServiceTest {

    @Mock
    PersonRepository personRepository;
    @Mock
    FireStationRepository fireStationRepository;
    @Mock
    MedicalRecordsRepository medicalRecordsRepository;
    @InjectMocks
    FloodStationService floodStationService;

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
        person1 = buildPerson("Harry", "Potter", "24 rue de l'église", "111");
        personMr1 = buildMr("Harry", "Potter", LocalDate.now().minusYears(17), List.of("Felix felicis"), List.of("Plumes de phénix"));
        person2 = buildPerson("Hermione", "Granger", "24 rue de l'église", "222");
        personMr2 = buildMr("Hermione", "Granger", LocalDate.now().minusYears(18), List.of(), List.of());
        person3 = buildPerson("Ron", "Weasley", "2 rue de l'église", "333");
        personMr3 = buildMr("Ron", "Weasley", LocalDate.now().minusYears(18), List.of(), List.of());

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
    void getResidentsByStations_shouldGroupByAddress_forStations1and2() {
        Map<String, List<ResidentDto>> result =
                floodStationService.getListResidentAndAddressAndMedicationByStation(List.of(1L, 2L));

        assertThat(result).containsOnlyKeys("24 rue de l'église", "2 rue de l'église");

        assertThat(result.get("24 rue de l'église"))
                .hasSize(2)
                .extracting("name", "phone", "age", "medications", "allergies")
                .containsExactlyInAnyOrder(
                        tuple("Potter", "111", 17, List.of("Felix felicis"), List.of("Plumes de phénix")),
                        tuple("Granger", "222", 18, List.of(), List.of()));

        assertThat(result.get("2 rue de l'église"))
                .hasSize(1)
                .extracting("name", "phone", "age", "medications", "allergies")
                .containsExactly(tuple("Weasley", "333", 18, List.of(), List.of()));
    }

    @Test
    void getResidentsByStations_shouldReturnOnlyStation1Addresses() {
        Map<String, List<ResidentDto>> result =
                floodStationService.getListResidentAndAddressAndMedicationByStation(List.of(1L));

        assertThat(result).containsOnlyKeys("24 rue de l'église");

        assertThat(result.get("24 rue de l'église"))
                .hasSize(2)
                .extracting("name", "phone", "age", "medications", "allergies")
                .containsExactlyInAnyOrder(
                        tuple("Potter", "111", 17, List.of("Felix felicis"), List.of("Plumes de phénix")),
                        tuple("Granger", "222", 18, List.of(), List.of()));
    }

    @Test
    void getResidentsByStations_shouldReturnEmptyMapForUnknownStation() {
        Map<String, List<ResidentDto>> result =
                floodStationService.getListResidentAndAddressAndMedicationByStation(List.of(12L));

        assertThat(result).isEmpty();
    }
}