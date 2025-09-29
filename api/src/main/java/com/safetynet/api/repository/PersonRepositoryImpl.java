package com.safetynet.api.repository;

import com.safetynet.api.model.Person;
import com.safetynet.api.repository.dto.DataDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ListIterator;

/**
 * Default in-memory {@link PersonRepository} implementation with
 * write-through JSON persistence.
 *
 * <p>Holds a live reference to the persons list from
 * {@link DataRepository#getAllData()}. Each CRUD operation writes the
 * entire dataset back to the JSON file through
 * {@link DataRepository#saveAllData(DataDto)}.</p>
 *
 * <p>Not thread-safe. Updates and deletions match entries by
 * <strong>case-sensitive</strong> pair (firstName, lastName) and affect
 * all matching entries.</p>
 */
@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final List<Person> allPerson;
    private final DataRepository dataRepository;

    /**
     * Creates the repository and loads the initial persons list from
     * the {@link DataRepository}.
     *
     * @param dataRepository source of the initial person data
     */
    public PersonRepositoryImpl(DataRepository dataRepository) {
        this.allPerson = dataRepository.getAllData().persons();
        this.dataRepository = dataRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Person> getAllPerson() {
        return allPerson;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPerson(Person person) {
        allPerson.add(person);
        saveData();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Iterates through the list and replaces every entry whose
     * first and last name match those of the provided {@link Person}.
     * If several records share the same names, all of them are replaced.
     * </p>
     */
    @Override
    public void updatePerson(Person person) {
        ListIterator<Person> iterator = allPerson.listIterator();
        while (iterator.hasNext()) {
            Person p = iterator.next();
            if (p.getFirstName().equals(person.getFirstName())
                    && p.getLastName().equals(person.getLastName())) {
                iterator.set(person);
            }
        }
        saveData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePerson(String firstName, String lastName) {
        allPerson.removeIf(p -> p.getFirstName().equals(firstName)
                && p.getLastName().equals(lastName));
        saveData();
    }

    /**
     * Persists the current state of all data (persons, fire stations,
     * medical records) to the JSON file.
     */
    private void saveData() {
        DataDto current = dataRepository.getAllData();
        DataDto toSave = new DataDto(allPerson, current.firestations(), current.medicalrecords());
        dataRepository.saveAllData(toSave);
    }
}