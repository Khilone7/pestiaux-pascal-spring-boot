package com.safetynet.api.service;

import com.safetynet.api.model.Person;
import com.safetynet.api.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityEmailService {

    private final PersonRepository personRepository;

    public List<String> getEmailByCity (String city){
        return personRepository.getAllPerson()
                .stream()
                .filter(p -> city.equals(p.getCity()))
                .map(Person::getEmail)
                .toList();
    }
}