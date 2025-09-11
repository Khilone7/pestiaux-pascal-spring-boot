package com.safetynet.api.service;

import com.safetynet.api.controller.dto.FireStationDto;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.FireStationRepository;
import com.safetynet.api.repository.MedicalRecordsRepository;
import com.safetynet.api.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    private static final Logger logger = LogManager.getLogger(FireStationService.class);

    private final PersonRepository personRepository;
    private final FireStationRepository fireStationRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;

    public FireStationService(PersonRepository personRepository, FireStationRepository fireStationRepository, MedicalRecordsRepository medicalRecordsRepository) {
        this.personRepository = personRepository;
        this.fireStationRepository = fireStationRepository;
        this.medicalRecordsRepository = medicalRecordsRepository;
    }

    public FireStationDto.StationDto getPersonsByStation(Long station) {

        List<String> address = addressesByStation(station);
        List<Person> listNonFilter = personByAddress(address);

        int child = childNumber(listNonFilter);
        int adult = adultNumber(listNonFilter, child);

        List<FireStationDto.PersonDto> listFilter = listNonFilter.stream().map(p -> new FireStationDto.PersonDto(p.getFirstName(), p.getLastName(), p.getAddress(), p.getPhone())).toList();

        return new FireStationDto.StationDto(listFilter, child, adult);
    }

    public List<String> addressesByStation(Long station) {
        return fireStationRepository.getAllFireStation()
                .stream()
                .filter(f -> station.equals(f.getStation()))
                .map(FireStation::getAddress)
                .toList();
    }

    public List<Person> personByAddress(List<String> address) {
        return personRepository.getAllPerson()
                .stream()
                .filter(p -> address.contains(p.getAddress()))
                .toList();
    }


    Map<String, LocalDate> builBirthdateMap() {
        return medicalRecordsRepository.getAllMedicalRecord()
                .stream()
                .collect(Collectors.toMap(m -> m.getFirstName() + m.getLastName(), MedicalRecord::getBirthdate));
    }

    public int childNumber(List<Person> listPerson) {
        int child = 0;
        LocalDate today = LocalDate.now();
        Map<String, LocalDate> birthdateMap = builBirthdateMap();

        for (Person person : listPerson) {
            String personName = person.getFirstName() + person.getLastName();
            LocalDate birthdate = birthdateMap.get(personName);
            if (calculateAge(birthdate, today) <= 18) {
                child++;
            }
        }
        logger.debug("Il y'a {} enfants dan la liste", child);
        return child;
    }


    public int adultNumber(List<Person> listPerson, int child) {
        int adult = listPerson.size() - child;
        logger.debug("Il y'a {} adultes dans la liste", adult);
        return adult;
    }

    public int calculateAge(LocalDate birthdate, LocalDate currentDate) {
        Period period = Period.between(birthdate, currentDate);
        logger.debug("La personne a {} ans", period.getYears());
        return period.getYears();
    }
}
