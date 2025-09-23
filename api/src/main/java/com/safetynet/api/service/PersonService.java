package com.safetynet.api.service;

import com.safetynet.api.controller.dto.FullNameDto;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    private boolean exists(String firstName,String lastName) {
        return personRepository.getAllPerson()
                .stream()
                .anyMatch(p -> p.getFirstName().equals(firstName)
                        && p.getLastName().equals(lastName));
    }

    public void addPerson(Person person) {
        if (exists(person.getFirstName(),person.getLastName())){
            throw new IllegalStateException("This personList already exists");
        } else {
            personRepository.addPerson(person);
        }
    }

    public void updatePerson(Person person){
        if (exists(person.getFirstName(),person.getLastName())){
            personRepository.updatePerson(person);
        } else {
            throw new NoSuchElementException("This personList does not exists");
        }
    }

    public void deletePerson(FullNameDto fullName){
        if (exists(fullName.firstName(), fullName.lastName())){
            personRepository.deletePerson(fullName.firstName(), fullName.lastName());
        } else {
            throw new NoSuchElementException("This personList does not exists");
        }
    }
}
