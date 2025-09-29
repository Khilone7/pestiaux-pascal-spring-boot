package com.safetynet.api.repository;

import com.safetynet.api.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CRUD access to {@link Person} records with immediate JSON persistence.
 *
 * <p>Data is loaded once at startup and kept in memory. Every call to
 * {@link #addPerson(Person)}, {@link #updatePerson(Person)} or
 * {@link #deletePerson(String, String)} also writes the whole dataset
 * back to the JSON file.</p>
 *
 * <p>Updates and deletions match entries by <strong>case-sensitive</strong>
 * pair (firstName, lastName). All matching entries are affected.</p>
 */
@Repository
public interface PersonRepository {

    /**
     * Returns the live in-memory list of persons.
     *
     * @return mutable list of {@link Person}
     */
    List<Person> getAllPerson();

    /**
     * Adds a new person and immediately persists the change.
     *
     * @param person Person object to add
     */
    void addPerson(Person person);

    /**
     * Replaces <strong>all</strong> entries whose (firstName, lastName)
     * match the given {@link Person}, then persists the change.
     *
     * @param person updated Person object
     */
    void updatePerson(Person person);

    /**
     * Removes <strong>all</strong> entries whose (firstName, lastName)
     * match the given values, then persists the change.
     *
     * @param firstName given name
     * @param lastName  family name
     */
    void deletePerson(String firstName, String lastName);
}
