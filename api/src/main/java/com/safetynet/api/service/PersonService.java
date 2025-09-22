package com.safetynet.api.service;

import com.safetynet.api.controller.dto.DeletePersonDto;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            throw new RuntimeException("This person already exists");
        } else {
            personRepository.addPerson(person);
        }
    }

    public void updatePerson(Person person){
        if (exists(person.getFirstName(),person.getLastName())){
            personRepository.updatePerson(person);
        } else {
            throw new RuntimeException("This person does not exists");
        }
    }

    public void deletePerson(DeletePersonDto fullName){
        if (exists(fullName.firstName(), fullName.lastName())){
            personRepository.deletePerson(fullName.firstName(), fullName.lastName());
        } else {
            throw new RuntimeException("This person does not exists");
        }
    }
}
