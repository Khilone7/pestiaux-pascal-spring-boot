package com.safetynet.api.service;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.FireStationRepository;
import com.safetynet.api.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Provides phone numbers of residents served by a specific fire station.
 * <p>
 * Combines data from {@link FireStationRepository} and {@link PersonRepository}
 * to find all addresses covered by a station and then collect the residents’ phone numbers.
 * </p>
 */
@Log4j2
@RequiredArgsConstructor
@Service
public class PhoneAlertService {

    private final FireStationRepository fireStationRepository;
    private final PersonRepository personRepository;

    /**
     * Returns all phone numbers of residents living at addresses served by
     * the specified fire station.
     *
     * @param station fire station number to look up
     * @return list of phone numbers for residents covered by this station
     */
    public List<String> getPhoneByStation(Long station){
        List<String> addresses = getAddressesByStation(station);
        return getPhoneByAddresses(addresses);
    }

    /**
     * Counts the number of distinct addresses served by the specified fire station.
     *
     * @param station fire station number to look up
     * @return number of distinct addresses covered by the station
     */
    public Long countAddressesByStation(Long station) {
        return  fireStationRepository.getAllFireStation()
                .stream()
                .filter(f -> station.equals(f.getStation()))
                .map(FireStation::getAddress)
                .distinct()
                .count();
    }

    /**
     * Returns all addresses served by the given fire station.
     *
     * @param station fire station number
     * @return list of addresses covered by the station
     */
    private List<String> getAddressesByStation(Long station) {
        List<String> addresses = fireStationRepository.getAllFireStation()
                .stream()
                .filter(f -> station.equals(f.getStation()))
                .map(FireStation::getAddress)
                .toList();
        log.debug("Station {} covers {} addresses", station, addresses.size());
        return addresses;
    }

    /**
     * Returns the phone numbers of all residents whose address is included
     * in the provided list of addresses.
     *
     * @param addresses list of addresses
     * @return list of phone numbers for the residents of these addresses
     */
    private List<String>getPhoneByAddresses(List<String>addresses){
        List<String> phones = personRepository.getAllPerson()
                .stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .map(Person::getPhone)
                .toList();
        log.debug("Found {} phone numbers for {} addresses", phones.size(), addresses.size());
        return phones;
    }
}