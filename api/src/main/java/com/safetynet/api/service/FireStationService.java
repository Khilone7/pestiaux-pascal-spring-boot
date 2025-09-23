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

@Log4j2
@RequiredArgsConstructor
@Service
public class FireStationService {

    private final PersonRepository personRepository;
    private final FireStationRepository fireStationRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;

    public StationDto getPersonsByStation(Long station) {
        List<String> address = addressesByStation(station);
        List<Person> listNonFilter = getPersonByAddresses(address);

        Long child = childNumber(listNonFilter);
        Long adult = adultNumber(listNonFilter, child);

        List<PersonDto> listFilter = listNonFilter.stream().map(p -> new PersonDto(p.getFirstName(), p.getLastName(), p.getAddress(), p.getPhone())).toList();
        return new StationDto(listFilter, child, adult);
    }

    private List<String> addressesByStation(Long station) {
        List<String> addresses = fireStationRepository.getAllFireStation()
                .stream()
                .filter(f -> station.equals(f.getStation()))
                .map(FireStation::getAddress)
                .toList();
        log.debug("Station {} covers {} addresses", station, addresses.size());
        return addresses;
    }

    private List<Person> getPersonByAddresses(List<String> address) {
        List<Person> persons = personRepository.getAllPerson()
                .stream()
                .filter(p -> address.contains(p.getAddress()))
                .toList();
        log.debug("There are {} persons across {} addresses", persons.size(), address.size());
        return persons;
    }

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


    private Long adultNumber(List<Person> listPerson, Long child) {
        Long adult = listPerson.size() - child;
        log.debug("There are {} adults in the list", adult);
        return adult;
    }

    private int calculateAge(LocalDate birthdate, LocalDate currentDate) {
        Period period = Period.between(birthdate, currentDate);
        return period.getYears();
    }

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

    public void deleteFireStation(DeleteFireStationDto fireStationDto) {
        if (fireStationDto.address().isPresent() == fireStationDto.station().isPresent()) {
            throw new IllegalArgumentException("Provide exactly one address or one station number.");
        }
        fireStationDto.address().ifPresent(fireStationRepository::deleteByAddress);
        fireStationDto.station().ifPresent(fireStationRepository::deleteByStationNumber);
    }
}