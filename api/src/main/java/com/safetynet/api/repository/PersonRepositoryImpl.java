package com.safetynet.api.repository;

import com.safetynet.api.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepositoryImpl implements PersonRepository{

    private final DataRepository dataRepository;

    public PersonRepositoryImpl(DataRepository dataRepository){
        this.dataRepository = dataRepository;
    }

    @Override
    public List<Person> getAllPerson() {
        return dataRepository.getAllData().persons();
    }
}
