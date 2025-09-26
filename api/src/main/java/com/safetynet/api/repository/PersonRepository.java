package com.safetynet.api.repository;

import com.safetynet.api.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Provides in-memory CRUD operations for {@link Person} entries.
 * <p>
 * This repository stores person records loaded at application startup
 * and keeps them in memory for the lifetime of the application.
 * No persistent storage is used.
 * </p>
 */
@Repository
public interface PersonRepository {

    /**
     * Returns all person records currently held in memory.
     *
     * @return list of {@link Person} objects
     */
    List<Person> getAllPerson();

    /**
     * Adds a new person record to the in-memory store.
     *
     * @param person person record to add
     */
    void addPerson(Person person);

    /**
     * Updates the person record that matches the same first and last name
     * as the provided {@link Person} instance.
     *
     * @param person person record containing the updated information
     */
    void updatePerson(Person person);

    /**
     * Deletes the person record identified by the specified first and last name.
     *
     * @param firstName given name of the person to remove
     * @param lastName  family name of the person to remove
     */
    void deletePerson(String firstName, String lastName);
}
