package com.safetynet.api.service;

import com.safetynet.api.controller.dto.FullNameDto;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * Provides create, update and delete operations for {@link Person} entries.
 * <p>
 * Uses {@link PersonRepository} as the in-memory data source and
 * enforces uniqueness on the (firstName, lastName) pair.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    /**
     * Adds a new person if no entry with the same first and last name exists.
     *
     * @param person person to add
     * @throws IllegalStateException if a person with the same names already exists
     */
    public void addPerson(Person person) {
        if (exists(person.getFirstName(),person.getLastName())){
            throw new IllegalStateException("This personList already exists");
        } else {
            personRepository.addPerson(person);
        }
    }

    /**
     * Updates the record of a person that matches the given first and last name.
     *
     * @param person person containing the updated information
     * @throws NoSuchElementException if no person with the given names exists
     */
    public void updatePerson(Person person){
        if (exists(person.getFirstName(),person.getLastName())){
            personRepository.updatePerson(person);
        } else {
            throw new NoSuchElementException("This personList does not exists");
        }
    }

    /**
     * Deletes the person record identified by the specified first and last name.
     *
     * @param fullName DTO carrying the first and last name of the person to delete
     * @throws NoSuchElementException if no person with the given names exists
     */
    public void deletePerson(FullNameDto fullName){
        if (exists(fullName.firstName(), fullName.lastName())){
            personRepository.deletePerson(fullName.firstName(), fullName.lastName());
        } else {
            throw new NoSuchElementException("This personList does not exists");
        }
    }

    /**
     * Checks whether a person exists for the specified first and last name.
     *
     * @param firstName given name to match
     * @param lastName  family name to match
     * @return {@code true} if a person with the same names exists, otherwise {@code false}
     */
    private boolean exists(String firstName,String lastName) {
        return personRepository.getAllPerson()
                .stream()
                .anyMatch(p -> p.getFirstName().equals(firstName)
                        && p.getLastName().equals(lastName));
    }
}
