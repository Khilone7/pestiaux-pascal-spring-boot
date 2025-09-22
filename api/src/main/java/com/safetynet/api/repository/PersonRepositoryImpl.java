package com.safetynet.api.repository;

import com.safetynet.api.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ListIterator;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final List<Person> allPerson;

    public PersonRepositoryImpl(DataRepository dataRepository) {
        this.allPerson = dataRepository.getAllData().persons();
    }

    @Override
    public List<Person> getAllPerson() {
        return allPerson;
    }

    @Override
    public void addPerson(Person person) {
        allPerson.add(person);
    }

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
    }
}
