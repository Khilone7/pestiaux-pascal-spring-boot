package com.safetynet.api.controller;

import com.safetynet.api.controller.dto.FullNameDto;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

/**
 * REST controller exposing CRUD operations for {@link MedicalRecord} entries.
 * <p>
 * Base path: <strong>/medicalRecord</strong>.
 * Delegates all operations to {@link MedicalRecordService}.
 * </p>
 */
@Log4j2
@RestController
@RequestMapping("/medicalRecord")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    /**
     * POST /medicalRecord
     * <p>
     * Creates a new medical record.
     * </p>
     *
     * @param medicalRecord medical record to create
     * @return HTTP 201 (Created) if the record is successfully created
     * @throws IllegalStateException if a record with the same first and last name already exists
     */
    @PostMapping
    public ResponseEntity<Void> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        log.info("REQUEST POST /medicalRecord");
        medicalRecordService.addMedicalRecord(medicalRecord);
        ResponseEntity<Void> response = ResponseEntity.status(201).build();
        log.info("RESPONSE POST /medicalRecord -> {} | {} successfully created", response.getStatusCode(), medicalRecord);
        return response;
    }

    /**
     * PUT /medicalRecord
     * <p>
     * Updates the medical record that matches the given first and last name.
     * </p>
     *
     * @param medicalRecord record containing the updated information
     * @return HTTP 204 (No Content) if the update is successful
     * @throws NoSuchElementException if no record with the given names exists
     */
    @PutMapping
    public ResponseEntity<Void> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        log.info("REQUEST PUT /medicalRecord");
        medicalRecordService.updateMedicalRecord(medicalRecord);
        ResponseEntity<Void> response = ResponseEntity.noContent().build();
        log.info("RESPONSE PUT /medicalRecord -> {} | {} successfully updated", response.getStatusCode(), medicalRecord);
        return response;
    }

    /**
     * DELETE /medicalRecord
     * <p>
     * Deletes the medical record identified by the specified first and last name.
     * </p>
     *
     * @param fullName DTO carrying the first and last name of the record to delete
     * @return HTTP 204 (No Content) if the deletion is successful
     * @throws NoSuchElementException if no record with the given names exists
     */
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
