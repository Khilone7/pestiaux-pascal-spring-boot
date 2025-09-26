package com.safetynet.api.repository;

import com.safetynet.api.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Provides in-memory CRUD operations for {@link MedicalRecord} entries.
 * <p>
 * This repository stores medical records loaded at application startup
 * and keeps them in memory for the lifetime of the application.
 * No persistent storage is used.
 * </p>
 */
@Repository
public interface MedicalRecordsRepository {

    /**
     * Returns all medical records currently held in memory.
     *
     * @return list of {@link MedicalRecord} objects
     */
    List<MedicalRecord> getAllMedicalRecord();

    /**
     * Adds a new medical record to the in-memory store.
     *
     * @param medicalRecord record to add
     */
    void addMedicalRecord(MedicalRecord medicalRecord);

    /**
     * Updates the medical record that matches the same first and last name
     * as the provided {@link MedicalRecord} instance.
     *
     * @param medicalRecord record containing the updated information
     */
    void updateMedicalRecord(MedicalRecord medicalRecord);

    /**
     * Deletes the medical record identified by the specified first and last name.
     *
     * @param firstName given name of the person whose record should be removed
     * @param lastName  family name of the person whose record should be removed
     */
    void deleteMedicalRecord(String firstName, String lastName);
}
