package com.safetynet.api.repository;

import com.safetynet.api.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Provides in-memory CRUD operations for {@link MedicalRecord} entries
 * with immediate JSON persistence.
 *
 * <p>Data is kept in memory and every add, update or delete also
 * writes the full dataset back to the JSON file.</p>
 */
@Repository
public interface MedicalRecordsRepository {

    /**
     * Returns the current in-memory list of medical records.
     *
     * @return mutable list of {@link MedicalRecord}
     */
    List<MedicalRecord> getAllMedicalRecord();

    /**
     * Adds a medical record and immediately persists the change.
     *
     * @param medicalRecord MedicalRecord object to add
     */
    void addMedicalRecord(MedicalRecord medicalRecord);

    /**
     * Updates all records whose (firstName, lastName) match the given
     * object, then persists the change.
     *
     * @param medicalRecord updated MedicalRecord object
     */
    void updateMedicalRecord(MedicalRecord medicalRecord);

    /**
     * Deletes all records whose (firstName, lastName) match the given
     * values, then persists the change.
     *
     * @param firstName given name to match
     * @param lastName  family name to match
     */
    void deleteMedicalRecord(String firstName, String lastName);
}
