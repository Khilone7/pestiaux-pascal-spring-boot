package com.safetynet.api.service;

import com.safetynet.api.model.Person;
import com.safetynet.api.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    private boolean exists(Person person) {
        return personRepository.getAllPerson()
                .stream()
                .anyMatch(p -> p.getFirstName().equals(person.getFirstName())
                        && p.getLastName().equals(person.getLastName()));
    }

    public void addPerson(Person person) {
        if (exists(person)){
            throw new RuntimeException("This person already exists");
        } else {
            personRepository.addPerson(person);
        }
    }

    public void updatePerson(Person person){
        if (exists(person)){
            personRepository.updatePerson(person);
        } else {
            throw new RuntimeException("This person does not exists");
        }
    }
}
