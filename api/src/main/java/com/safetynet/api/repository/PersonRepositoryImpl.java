package com.safetynet.api.repository;

import com.safetynet.api.model.Person;
import com.safetynet.api.repository.dto.DataDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ListIterator;

/**
 * Default in-memory implementation of {@link PersonRepository}.
 * <p>
 * Loads all person records from the JSON file at application startup
 * through {@link DataRepository} and keeps them in memory for the entire
 * lifetime of the application. No further disk access or persistence occurs.
 * </p>
 */
@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final List<Person> allPerson;
    private final DataRepository dataRepository;

    /**
     * Initializes the repository by retrieving the person list
     * from the {@link DataRepository}.
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

    private void saveData() {
        DataDto current = dataRepository.getAllData();
        DataDto toSave = new DataDto(allPerson, current.firestations(), current.medicalrecords());
        dataRepository.saveAllData(toSave);
    }
}