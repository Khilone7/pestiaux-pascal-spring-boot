package com.safetynet.api.repository;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DataRepository {

    List<Person> getAllPerson();

    List<FireStation> getAllFireStation();

    List<MedicalRecord> getAllMedicalRecord();

}
