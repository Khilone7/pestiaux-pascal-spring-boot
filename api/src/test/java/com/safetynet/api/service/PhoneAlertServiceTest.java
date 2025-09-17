package com.safetynet.api.service;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.FireStationRepository;
import com.safetynet.api.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhoneAlertServiceTest {

    @Mock
    PersonRepository personRepository;
    @Mock
    FireStationRepository fireStationRepository;
    @InjectMocks
    PhoneAlertService phoneAlertService;

    Person person1, person2, person3;
    FireStation station1, station2;

    @BeforeEach
    void setUp() {
        person1 = new Person();
        person1.setAddress("24 rue de l'église");
        person1.setPhone("111");

        person2 = new Person();
        person2.setAddress("24 rue de l'église");
        person2.setPhone("222");

        person3 = new Person();
        person3.setAddress("2 rue de l'église");
        person3.setPhone("333");

        station1 = FireStation.builder().station(1L).address("24 rue de l'église").build();
        station2 = FireStation.builder().station(2L).address("2 rue de l'église").build();

        when(personRepository.getAllPerson()).thenReturn(List.of(person1, person2, person3));
        when(fireStationRepository.getAllFireStation()).thenReturn(List.of(station1, station2));
    }

    @Test
    void getPhoneByStationShouldReturnTwoPhone() {
        List<String> result = phoneAlertService.getPhoneByStation(1L);

        assertThat(result).containsExactlyInAnyOrder("111", "222");
    }

    @Test
    void getPhoneByStationShouldReturnNothingWhenWrongStation() {
        List<String> result = phoneAlertService.getPhoneByStation(12L);

        assertThat(result).isEmpty();

    }

}