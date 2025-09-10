package com.safetynet.api.repository;

import com.safetynet.api.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository {

    List<Person> getAllPerson();
}
