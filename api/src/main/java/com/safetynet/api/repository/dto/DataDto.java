package com.safetynet.api.repository.dto;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;

import java.util.List;

public record DataDto(List<Person> persons, List<FireStation> firestations, List<MedicalRecord>medicalrecords){}