package com.safetynet.api.service;

import com.safetynet.api.controller.dto.AdultDto;
import com.safetynet.api.controller.dto.ChildAlertResponseDto;
import com.safetynet.api.controller.dto.ChildDto;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.MedicalRecordsRepository;
import com.safetynet.api.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Computes the list of children and co-resident adults for a given address.
 * <p>
 * Children are defined as persons aged ≤ 18;
 * adults are persons aged > 18. Ages are derived from
 * {@link MedicalRecord#getBirthdate()} at the current system date.
 * </p>
 *
 * <p><strong>Assumptions:</strong> each {@link Person} must have a matching
 * {@link MedicalRecord} (same first and last name); otherwise age computation
 * will fail at runtime.</p>
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class ChildAlertService {

    private final MedicalRecordsRepository medicalRecordsRepository;
    private final PersonRepository personRepository;

    /**
     * Returns the children (≤ 18 years) living at the given address along with
     * the adults (> 18 years) co-residing at the same address.
     * <p>
     * If no children are found, both lists in the response are empty.
     * </p>
     *
     * @param address street address to match exactly
     * @return response containing the child list and the adult list
     */
    public ChildAlertResponseDto getChildByAddress(String address) {
        List<Person> person = getPersonByAddress(address);
        Map<Person, Integer> personAge = getPersonAge(person);

        List<ChildDto> childList = getChildList(personAge);
        if (childList.isEmpty()) {
            return new ChildAlertResponseDto(Collections.emptyList(), Collections.emptyList());
        }
        List<AdultDto> adultList = getAdultList(personAge);

        return new ChildAlertResponseDto(childList, adultList);
    }

    /**
     * Retrieves all persons whose address equals the provided value.
     * <p>Matching is a simple string equality check.</p>
     *
     * @param address street address to match exactly
     * @return list of persons living at the given address
     */
    private List<Person> getPersonByAddress(String address) {
        List<Person> personList = personRepository.getAllPerson()
                .stream()
                .filter(p -> p.getAddress().equals(address))
                .toList();
        log.debug("Found {} persons living at address '{}'", personList.size(), address);
        return personList;
    }

    /**
     * Builds a map of persons to their integer age in years.
     * <p>
     * Ages are computed from the matching {@link MedicalRecord} birthdate
     * using {@link Period#between(LocalDate, LocalDate)} with today's date.
     * </p>
     * <p><strong>Assumption:</strong> for each person, a medical record exists;
     * otherwise a runtime failure will occur when accessing the birthdate.</p>
     *
     * @param personList persons for whom ages should be computed
     * @return map from person to age in completed years
     */
    private Map<Person, Integer> getPersonAge(List<Person> personList) {
        Map<String, LocalDate> mapPersonAge = medicalRecordsRepository.getAllMedicalRecord()
                .stream()
                .collect(Collectors.toMap(m -> m.getFirstName() + m.getLastName(), MedicalRecord::getBirthdate));

        return personList
                .stream()
                .collect(Collectors.toMap(p -> p, p -> Period.between(mapPersonAge.get(p.getFirstName() + p.getLastName()), LocalDate.now()).getYears()));
    }

    /**
     * Extracts all children (age ≤ 18) from the provided person-to-age map.
     *
     * @param personAgeMap map of persons to ages in years
     * @return list of child DTOs (first name, last name, age)
     */
    private List<ChildDto> getChildList(Map<Person, Integer> personAgeMap) {
        List<ChildDto> childrenList = personAgeMap.entrySet()
                .stream()
                .filter(p -> p.getValue() <= 18)
                .map(p -> new ChildDto(p.getKey().getFirstName(), p.getKey().getLastName(), p.getValue()))
                .toList();
        log.debug("There are {} children", childrenList.size());
        return childrenList;
    }

    /**
     * Extracts all adults (age > 18) from the provided person-to-age map.
     *
     * @param personAgeMap map of persons to ages in years
     * @return list of adult DTOs (first name, last name)
     */
    private List<AdultDto> getAdultList(Map<Person, Integer> personAgeMap) {
        List<AdultDto> adultList = personAgeMap.entrySet()
                .stream()
                .filter(p -> p.getValue() > 18)
                .map(p -> new AdultDto(p.getKey().getFirstName(), p.getKey().getLastName()))
                .toList();
        log.debug("There are {} adults", adultList.size());
        return adultList;
    }
}