package com.safetynet.api.repository;

import com.safetynet.api.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicalRecordsRepositoryImpl implements MedicalRecordsRepository {

    private final DataRepository dataRepository;

    public MedicalRecordsRepositoryImpl(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @Override
    public List<MedicalRecord> getAllMedicalRecord(){
        return dataRepository.getAllData().medicalrecords();
    }
}
