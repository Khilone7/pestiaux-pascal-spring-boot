package com.safetynet.api.controller;

import com.safetynet.api.controller.dto.FullNameDto;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicalRecord")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @PostMapping
    public ResponseEntity<Void> addMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        medicalRecordService.addMedicalRecord(medicalRecord);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        medicalRecordService.updateMedicalRecord(medicalRecord);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMedicalRecord(@RequestBody FullNameDto fullName){
        medicalRecordService.deleteMedicalRecord(fullName);
        return ResponseEntity.ok().build();
    }
}
