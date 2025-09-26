package com.safetynet.api.repository.dto;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;

import java.util.List;

/**
 * Immutable container for all application data loaded from the JSON file.
 * <p>Holds the lists of persons, fire stations and medical records exactly
 * as they appear in the source file.</p>
 *
 * @param persons       all persons defined in the data source
 * @param firestations  all fire station mappings defined in the data source
 * @param medicalrecords all medical records defined in the data source
 */
public record DataDto(List<Person> persons, List<FireStation> firestations, List<MedicalRecord>medicalrecords){}