package com.safetynet.api.service;

import com.safetynet.api.model.Person;
import com.safetynet.api.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Provides access to the list of email addresses of all persons
 * living in a specified city.
 * <p>
 * Uses the {@link PersonRepository} as data source and performs
 * a simple equality check on the city name.
 * </p>
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class CommunityEmailService {

    private final PersonRepository personRepository;

    /**
     * Returns the email addresses of all persons whose city exactly
     * matches the given name.
     * <p>
     * The comparison is case-sensitive and based on simple string equality.
     * </p>
     *
     * @param city name of the city to match exactly
     * @return list of email addresses of the residents of the specified city
     */
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