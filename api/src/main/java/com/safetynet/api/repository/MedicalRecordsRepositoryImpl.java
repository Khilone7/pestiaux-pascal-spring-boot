package com.safetynet.api.repository;

import com.safetynet.api.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ListIterator;

@Repository
public class MedicalRecordsRepositoryImpl implements MedicalRecordsRepository {

    private final List<MedicalRecord> medicalRecordList;

    public MedicalRecordsRepositoryImpl(DataRepository dataRepository) {
        this.medicalRecordList = dataRepository.getAllData().medicalrecords();
    }

    @Override
    public List<MedicalRecord> getAllMedicalRecord(){
        return medicalRecordList;
    }

    @Override
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordList.add(medicalRecord);
    }

    @Override
    public void updateMedicalRecord(MedicalRecord medicalRecord) {
        ListIterator<MedicalRecord> iterator = medicalRecordList.listIterator();
        while (iterator.hasNext()) {
            MedicalRecord mr = iterator.next();
            if (mr.getFirstName().equals(medicalRecord.getFirstName())
                    && mr.getLastName().equals(medicalRecord.getLastName())) {
                iterator.set(medicalRecord);
            }
        }
    }

    @Override
    public void deleteMedicalRecord(String firstName, String lastName) {
        medicalRecordList.removeIf(mr -> mr.getFirstName().equals(firstName)
                && mr.getLastName().equals(lastName));
    }
}