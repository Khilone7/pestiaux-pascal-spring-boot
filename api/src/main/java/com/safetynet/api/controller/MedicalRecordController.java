package com.safetynet.api.controller;

import com.safetynet.api.controller.dto.FullNameDto;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/medicalRecord")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @PostMapping
    public ResponseEntity<Void> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        log.info("REQUEST POST /medicalRecord");
        medicalRecordService.addMedicalRecord(medicalRecord);
        ResponseEntity<Void> response = ResponseEntity.status(201).build();
        log.info("RESPONSE POST /medicalRecord -> {} | {} successfully created", response.getStatusCode(), medicalRecord);
        return response;
    }

    @PutMapping
    public ResponseEntity<Void> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        log.info("REQUEST PUT /medicalRecord");
        medicalRecordService.updateMedicalRecord(medicalRecord);
        ResponseEntity<Void> response = ResponseEntity.noContent().build();
        log.info("RESPONSE PUT /medicalRecord -> {} | {} successfully updated", response.getStatusCode(), medicalRecord);
        return response;
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMedicalRecord(@RequestBody FullNameDto fullName) {
        log.info("REQUEST DELETE /medicalRecord");
        medicalRecordService.deleteMedicalRecord(fullName);
        ResponseEntity<Void> response = ResponseEntity.noContent().build();
        log.info("RESPONSE DELETE /medicalRecord -> {} | {} {} medical record successfully deleted",
                response.getStatusCode(), fullName.firstName(),fullName.lastName());
        return response;
    }
}
