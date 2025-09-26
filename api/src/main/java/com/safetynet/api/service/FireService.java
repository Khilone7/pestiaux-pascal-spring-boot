package com.safetynet.api.service;

import com.safetynet.api.controller.dto.ListPersonAndStationDto;
import com.safetynet.api.controller.dto.PersonAndMedicationDto;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.FireStationRepository;
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
 * Provides information about residents and the fire station serving a specific address.
 * <p>
 * Combines data from {@link PersonRepository}, {@link FireStationRepository} and
 * {@link MedicalRecordsRepository} to produce a list of residents (with age and
 * medical details) together with the number of the fire station that covers the
 * given address.
 * </p>
 */
@Log4j2
@RequiredArgsConstructor
@Service
public class FireService {

    private final PersonRepository personRepository;
    private final FireStationRepository fireStationRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;

    /**
     * Returns the list of residents for the specified address and the number of
     * the fire station that covers it.
     * <p>
     * Each resident is enriched with age and medical information taken from
     * {@link MedicalRecord}.
     * </p>
     *
     * @param address street address to look up
     * @return a {@link ListPersonAndStationDto} containing
     *         the residents’ details and the station number;
     *         if no station covers the address, the person list is empty and
     *         the station number is {@code null}
     * @throws IllegalStateException if a resident has no matching medical record
     */
    public ListPersonAndStationDto getPersonAndStationByAddress(String address) {
        Long stationNumber = getStationNumberByAddress(address);
        if (stationNumber == null) {
            return new ListPersonAndStationDto(Collections.emptyList(), null);
        }

        List<Person> personList = getPersonByAddress(address);
        if (personList.isEmpty()) {
            return new ListPersonAndStationDto(Collections.emptyList(), stationNumber);
        }

        Map<String, MedicalRecord> medicalRecordMap = medicalRecordsRepository.getAllMedicalRecord()
                .stream()
                .collect(Collectors.toMap(m -> m.getFirstName() + m.getLastName(), m -> m));

        List<PersonAndMedicationDto> personDtoList = personList
                .stream()
                .map(p -> {
                    MedicalRecord m = medicalRecordMap.get(p.getFirstName() + p.getLastName());
                    if (m == null) {
                        throw new IllegalStateException("Missing medical record for "+ p.getFirstName() + " " + p.getLastName());
                    }
                    return buildPersonDto(p, m);
                })
                .toList();
        return new ListPersonAndStationDto(personDtoList, stationNumber);
    }

    /**
     * Builds a {@link PersonAndMedicationDto} with the resident’s last name,
     * phone number, age (in full years) and medical information.
     *
     * @param person         resident whose information is to be included
     * @param medicalRecord  resident’s medical record
     * @return a fully populated {@link PersonAndMedicationDto}
     */
    private PersonAndMedicationDto buildPersonDto(Person person, MedicalRecord medicalRecord) {
        int age = Period.between(medicalRecord.getBirthdate(), LocalDate.now()).getYears();
        return new PersonAndMedicationDto(person.getLastName(), person.getPhone(), age, medicalRecord.getMedications(), medicalRecord.getAllergies());
    }

    /**
     * Finds the fire station number that covers the given address.
     *
     * @param address street address to match exactly
     * @return the station number, or {@code null} if no station covers the address
     */
    private Long getStationNumberByAddress(String address) {
        Long station = fireStationRepository.getAllFireStation()
                .stream()
                .filter(f -> f.getAddress().equals(address))
                .map(FireStation::getStation)
                .findFirst()
                .orElse(null);

        log.debug("Station {} covers address '{}'", station, address);
        return station;
    }

    /**
     * Returns all persons whose address matches the given value.
     * <p>
     * Matching is a simple string equality check.
     * </p>
     *
     * @param address street address to match exactly
     * @return list of persons living at the given address
     */
    private List<Person> getPersonByAddress(String address) {
        List<Person> list = personRepository.getAllPerson()
                .stream()
                .filter(p -> address.equals(p.getAddress()))
                .toList();

        log.debug("There are {} persons for address '{}'", list.size(), address);
        return list;
    }
}