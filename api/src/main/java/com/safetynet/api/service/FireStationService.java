package com.safetynet.api.service;

import com.safetynet.api.dto.FireStationDto;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;


@org.springframework.stereotype.Service
public class FireStationService {


    private final DataRepository dataRepository;

    public FireStationService(DataRepository dataRepository) {
        this.dataRepository = dataRepository; // injecté par Spring
    }

    public FireStationDto.StationDto urlNumeroUN(byte station){

        List<Person> listNonFilter= getPersonsByStation(station);

        int child =childNumber(listNonFilter);
        int adult =adultNumber(listNonFilter,child);

        List<FireStationDto.PersonDto> listFilter = listNonFilter.stream().map(p -> new FireStationDto.PersonDto(p.getFirstName(),p.getLastName(),p.getAddress(),p.getPhone())).toList();


        return new FireStationDto.StationDto(listFilter,child,adult);
    }


    public List<Person> getPersonsByStation(byte station) {
        List<Person> personListFilter = new ArrayList<>();
        List<String> addresses = new ArrayList<>();

        for (FireStation fireStation : dataRepository.getAllFireStation()) {
            if (fireStation.getStation() == station) {
                addresses.add(fireStation.getAddress());
            }
        }

        for (Person person : dataRepository.getAllPerson()) {
            for (String address : addresses) {
                if (person.getAddress().equals(address)) {
                    personListFilter.add(person);
                }
            }
        }
        return personListFilter;
    }

    public int childNumber(List<Person> listPerson) {
        int child = 0;
        for (Person person : listPerson) {
            for (MedicalRecord medicalRecord : dataRepository.getAllMedicalRecord()) {
                if (person.getFirstName().equals(medicalRecord.getFirstName()) && person.getLastName().equals(medicalRecord.getLastName())) {
                    if (calculateAge(medicalRecord.getBirthdate(), LocalDate.now()) <= 18) {
                        child = child + 1;
                    }
                }
            }
        }
        return child;
    }

    public int adultNumber (List<Person> listPerson,int child){
        return listPerson.size()-child;
    }

    public int calculateAge(LocalDate birthdate, LocalDate currentDate) {
        Period period = Period.between(birthdate, currentDate);
        return period.getYears();
    }
}
