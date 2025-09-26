package com.safetynet.api.service;

import com.safetynet.api.controller.dto.DeleteFireStationDto;
import com.safetynet.api.controller.dto.PersonDto;
import com.safetynet.api.controller.dto.StationDto;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides resident listings and child/adult counts for a given fire station number,
 * and exposes basic CRUD-like operations for fire station mappings.
 * <p>
 * Aggregates data from {@link PersonRepository}, {@link FireStationRepository} and
 * {@link MedicalRecordsRepository}. Children are defined as age ≤ 18; adults as age > 18.
 * Ages are computed from {@link MedicalRecord#getBirthdate()} relative to the current date.
 * </p>
 */
@Log4j2
@RequiredArgsConstructor
@Service
public class FireStationService {

    private final PersonRepository personRepository;
    private final FireStationRepository fireStationRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;

    /**
     * Returns all residents served by the specified fire station together with
     * the number of children (age ≤ 18) and adults (age > 18).
     * <p>
     * If no address is covered by the station, the resident list is empty and both
     * counters are {@code null}.
     * </p>
     *
     * @param station station number to look up
     * @return a {@link StationDto} containing residents and child/adult counts
     */
    public StationDto getPersonsByStation(Long station) {
        List<String> address = addressesByStation(station);
        if (address.isEmpty()) {
            return new StationDto(Collections.emptyList(), null, null);
        }
        List<Person> listNonFilter = getPersonByAddresses(address);

        Long child = childNumber(listNonFilter);
        Long adult = adultNumber(listNonFilter, child);

        List<PersonDto> listFilter = listNonFilter.stream().map(p -> new PersonDto(p.getFirstName(), p.getLastName(), p.getAddress(), p.getPhone())).toList();
        return new StationDto(listFilter, child, adult);
    }

    /**
     * Adds a new fire-station mapping.
     * <p>
     * Throws an exception if a mapping with the same address and station already exists.
     * </p>
     *
     * @param fireStation mapping to add
     * @throws IllegalStateException if the same address/station pair already exists
     */
    public void addFireStation(FireStation fireStation){
        boolean exists = fireStationRepository.getAllFireStation()
                .stream()
                .anyMatch(f -> f.getAddress().equals(fireStation.getAddress())
                        && f.getStation().equals(fireStation.getStation()));
        if (exists){
            throw new IllegalStateException("This fire station already exist");
        }else{
            fireStationRepository.addFireStation(fireStation);
        }
    }

    /**
     * Updates the station number associated with an address.
     * <p>
     * Throws an exception if no mapping exists for the given address.
     * </p>
     *
     * @param fireStation object carrying the target address and the new station number
     * @throws NoSuchElementException if no mapping exists for the address
     */
    public void updateFireStationNumber(FireStation fireStation){
        boolean exists = fireStationRepository.getAllFireStation()
                .stream()
                .anyMatch(f -> f.getAddress().equals(fireStation.getAddress()));
        if (exists){
            fireStationRepository.updateStationNumber(fireStation);
        }else {
            throw new NoSuchElementException("This fire station does not exists");
        }
    }

    /**
     * Deletes a fire-station mapping by either address or station number.
     * <p>
     * Exactly one of the two optional fields must be provided; providing both or none
     * results in an exception.
     * </p>
     *
     * @param fireStationDto wrapper carrying either an address or a station number
     * @throws IllegalArgumentException if both fields are present or both are absent
     */
    public void deleteFireStation(DeleteFireStationDto fireStationDto) {
        if (fireStationDto.address().isPresent() == fireStationDto.station().isPresent()) {
            throw new IllegalArgumentException("Provide exactly one address or one station number.");
        }
        fireStationDto.address().ifPresent(fireStationRepository::deleteByAddress);
        fireStationDto.station().ifPresent(fireStationRepository::deleteByStationNumber);
    }

    /**
     * Returns the list of addresses covered by the given station.
     *
     * @param station station number
     * @return addresses mapped to this station
     */
    private List<String> addressesByStation(Long station) {
        List<String> addresses = fireStationRepository.getAllFireStation()
                .stream()
                .filter(f -> station.equals(f.getStation()))
                .map(FireStation::getAddress)
                .toList();
        log.debug("Station {} covers {} addresses", station, addresses.size());
        return addresses;
    }

    /**
     * Returns all persons whose address is contained in the provided address list.
     *
     * @param address list of addresses
     * @return residents living at any of the provided addresses
     */
    private List<Person> getPersonByAddresses(List<String> address) {
        List<Person> persons = personRepository.getAllPerson()
                .stream()
                .filter(p -> address.contains(p.getAddress()))
                .toList();
        log.debug("There are {} persons across {} addresses", persons.size(), address.size());
        return persons;
    }

    /**
     * Counts how many persons are children (age ≤ 18).
     *
     * @param listPerson residents to analyze
     * @return number of children
     */
    private Long childNumber(List<Person> listPerson) {
        LocalDate today = LocalDate.now();
        Map<String, LocalDate> birthdateMap = medicalRecordsRepository.getAllMedicalRecord()
                .stream()
                .collect(Collectors.toMap(m -> m.getFirstName() + m.getLastName(), MedicalRecord::getBirthdate));

        Long child = listPerson
                .stream()
                .filter(p -> {
                    String personName = p.getFirstName()+p.getLastName();
                    LocalDate birthdate = birthdateMap.get(personName);
                    return calculateAge(birthdate,today) <= 18;
                })
                .count();

        log.debug("There are {} children in the list", child);
        return child;
    }

    /**
     * Computes the number of adults (age > 18) given the total residents and the child count.
     *
     * @param listPerson residents to analyze
     * @param child      number of children among {@code listPerson}
     * @return number of adults
     */
    private Long adultNumber(List<Person> listPerson, Long child) {
        Long adult = listPerson.size() - child;
        log.debug("There are {} adults in the list", adult);
        return adult;
    }

    /**
     * Returns full years between the birthdate and the current date.
     *
     * @param birthdate   date of birth
     * @param currentDate reference date for the computation
     * @return age in completed years
     */
    private int calculateAge(LocalDate birthdate, LocalDate currentDate) {
        Period period = Period.between(birthdate, currentDate);
        return period.getYears();
    }
}