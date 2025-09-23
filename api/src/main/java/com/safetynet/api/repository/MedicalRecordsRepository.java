package com.safetynet.api.repository;

import com.safetynet.api.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordsRepository {
    List<MedicalRecord> getAllMedicalRecord();
    void addMedicalRecord(MedicalRecord medicalRecord);
    void updateMedicalRecord(MedicalRecord medicalRecord);
    void deleteMedicalRecord(String firstName, String lastName);
}
