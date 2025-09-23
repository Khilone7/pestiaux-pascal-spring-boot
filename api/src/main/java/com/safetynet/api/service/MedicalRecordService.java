package com.safetynet.api.service;

import com.safetynet.api.controller.dto.FullNameDto;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.repository.MedicalRecordsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void addMedicalRecord(MedicalRecord medicalRecord) {
        if(exists(medicalRecord.getFirstName(),medicalRecord.getLastName())){
            throw new RuntimeException("This medical record already exists");
        }else{
            medicalRecordsRepository.addMedicalRecord(medicalRecord);
        }
    }

    public void updateMedicalRecord(MedicalRecord medicalRecord){
        if (exists(medicalRecord.getFirstName(),medicalRecord.getLastName())){
            medicalRecordsRepository.updateMedicalRecord(medicalRecord);
        }else {
            throw new RuntimeException("This medical record does not exists");
        }
    }

    public void deleteMedicalRecord(FullNameDto fullName){
        if (exists(fullName.firstName(),fullName.lastName())){
            medicalRecordsRepository.deleteMedicalRecord(fullName.firstName(), fullName.lastName());
        }else {
            throw new RuntimeException("This medical record does not exists");
        }
    }

    public List<MedicalRecord> getAllMr(){
        return medicalRecordsRepository.getAllMedicalRecord();
    }
}
