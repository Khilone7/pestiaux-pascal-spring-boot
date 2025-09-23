package com.safetynet.api.service;

import com.safetynet.api.model.Person;
import com.safetynet.api.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class CommunityEmailService {

    private final PersonRepository personRepository;

    public List<String> getEmailByCity(String city) {
        List<String> emails = personRepository.getAllPerson()
                .stream()
                .filter(p -> city.equals(p.getCity()))
                .map(Person::getEmail)
                .toList();
        log.debug("Found {} emails for city '{}'", emails.size(), city);
        return emails;
    }
}