package com.safetynet.api.service;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.FireStationRepository;
import com.safetynet.api.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class PhoneAlertService {

    private final FireStationRepository fireStationRepository;
    private final PersonRepository personRepository;

    public List<String> getPhoneByStation(Long station){
        List<String> addresses = getAddressesByStation(station);
        return getPhoneByAddresses(addresses);
    }

    public Long countAddressesByStation(Long station) {
        return  fireStationRepository.getAllFireStation()
                .stream()
                .filter(f -> station.equals(f.getStation()))
                .map(FireStation::getAddress)
                .distinct()
                .count();
    }

    private List<String> getAddressesByStation(Long station) {
        List<String> addresses = fireStationRepository.getAllFireStation()
                .stream()
                .filter(f -> station.equals(f.getStation()))
                .map(FireStation::getAddress)
                .toList();
        log.debug("Station {} covers {} addresses", station, addresses.size());
        return addresses;
    }

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