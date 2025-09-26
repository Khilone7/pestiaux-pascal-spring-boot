package com.safetynet.api.service;

import com.safetynet.api.controller.dto.FullNameDto;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.repository.MedicalRecordsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * Provides create, update and delete operations for {@link MedicalRecord} entries.
 * <p>
 * Uses {@link MedicalRecordsRepository} as the in-memory data source and
 * enforces uniqueness on the (firstName, lastName) pair.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class MedicalRecordService {

    private final MedicalRecordsRepository medicalRecordsRepository;

    private boolean exists(String firstName,String lastName) {
        return medicalRecordsRepository.getAllMedicalRecord()
                .stream()
                .anyMatch(mr -> mr.getFirstName().equals(firstName)
                        && mr.getLastName().equals(lastName));
    }

    /**
     * Adds a new medical record if no record with the same first and last name exists.
     *
     * @param medicalRecord medical record to add
     * @throws IllegalStateException if a record with the same first and last name already exists
     */
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        if(exists(medicalRecord.getFirstName(),medicalRecord.getLastName())){
            throw new IllegalStateException("This medical record already exists");
        }else{
            medicalRecordsRepository.addMedicalRecord(medicalRecord);
        }
    }

    /**
     * Updates an existing medical record that matches the given first and last name.
     *
     * @param medicalRecord medical record containing the updated information
     * @throws NoSuchElementException if no record with the given names exists
     */
    public void updateMedicalRecord(MedicalRecord medicalRecord){
        if (exists(medicalRecord.getFirstName(),medicalRecord.getLastName())){
            medicalRecordsRepository.updateMedicalRecord(medicalRecord);
        }else {
            throw new NoSuchElementException("This medical record does not exists");
        }
    }

    /**
     * Deletes the medical record identified by the specified first and last name.
     *
     * @param fullName DTO carrying the first and last name of the record to delete
     * @throws NoSuchElementException if no record with the given names exists
     */
    public void deleteMedicalRecord(FullNameDto fullName){
        if (exists(fullName.firstName(),fullName.lastName())){
            medicalRecordsRepository.deleteMedicalRecord(fullName.firstName(), fullName.lastName());
        }else {
            throw new NoSuchElementException("This medical record does not exists");
        }
    }
}
