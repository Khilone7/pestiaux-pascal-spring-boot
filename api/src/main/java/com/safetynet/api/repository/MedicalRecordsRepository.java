package com.safetynet.api.repository;

import com.safetynet.api.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordsRepository {
    List<MedicalRecord> getAllMedicalRecord();
}
