package com.safetynet.api.service;

import com.safetynet.api.model.Person;
import com.safetynet.api.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommunityEmailServiceTest {

    @Mock
    PersonRepository personRepository;
    @InjectMocks
    CommunityEmailService communityEmailService;

    Person person1, person2;

    @BeforeEach
    void setUp() {
        person1 = new Person();
        person1.setEmail("1111@gmail.com");
        person1.setCity("Paris");

        person2 = new Person();
        person2.setEmail("2222@gmail.com");
        person2.setCity("Marseille");

        when(personRepository.getAllPerson()).thenReturn(List.of(person1,person2));
    }

    @Test
    void getEmailByCityShouldReturnEmail1(){
        List<String> result = communityEmailService.getEmailByCity("Paris");
        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).contains("1111@gmail.com");
    }

    @Test
    void getEmailByCityShouldReturnNothingWhenZipIncomplete(){
        List<String> result = communityEmailService.getEmailByCity("Pars");
        assertTrue(result.isEmpty());
    }

    @Test
    void getEmailByCityShouldReturnNothingBecauseWrongCity(){
        List<String> result = communityEmailService.getEmailByCity("Lille");
        assertTrue(result.isEmpty());
    }
}