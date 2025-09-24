package com.safetynet.api.Integration;

import com.safetynet.api.controller.dto.FullNameDto;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.repository.MedicalRecordsRepository;
import com.safetynet.api.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MedicalRecordServiceIT {

    @Autowired
    MedicalRecordsRepository medicalRecordsRepository;
    @Autowired
    MedicalRecordService medicalRecordService;

    MedicalRecord charles, john, lewis;

    @BeforeEach
    void setUp() {
        charles = new MedicalRecord();
        charles.setFirstName("Charles");
        charles.setLastName("Leclerc");
        charles.setBirthdate(LocalDate.of(1997, 10, 16));
        charles.setMedications(List.of("ibuprofen:200mg"));
        charles.setAllergies(List.of("pollen"));

        john = new MedicalRecord();
        john.setFirstName("John");
        john.setLastName("Boyd");
        john.setBirthdate(LocalDate.of(1984, 3, 6));
        john.setMedications(List.of("aspirin:100mg"));
        john.setAllergies(List.of());

        lewis = new MedicalRecord();
        lewis.setFirstName("Lewis");
        lewis.setLastName("Hamilton");
        lewis.setBirthdate(LocalDate.of(1985, 1, 7));
        lewis.setMedications(List.of());
        lewis.setAllergies(List.of("nuts"));
    }

    @Test
    void addPersonShouldIncreaseListSize() {
        int sizeBefore = medicalRecordsRepository.getAllMedicalRecord().size();

        medicalRecordService.addMedicalRecord(charles);
        assertThat(medicalRecordsRepository.getAllMedicalRecord()).hasSize(sizeBefore + 1);
    }

    @Test
    void addExistingPersonShouldThrow() {
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> medicalRecordService.addMedicalRecord(john));
    }

    @Test
    void updatePersonShouldModifyPhone() {
        int sizeBefore = medicalRecordsRepository.getAllMedicalRecord().size();
        john.setAllergies(List.of("Pollen"));

        medicalRecordService.updateMedicalRecord(john);
        assertThat(medicalRecordsRepository.getAllMedicalRecord()).hasSize(sizeBefore);
        assertTrue(medicalRecordsRepository.getAllMedicalRecord()
                .stream()
                .anyMatch(mr -> mr.getFirstName().equals("John")
                        && mr.getLastName().equals("Boyd")
                        && mr.getAllergies().contains("Pollen")));
    }

    @Test
    void updateNonExistingPersonShouldThrow() {
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> medicalRecordService.updateMedicalRecord(lewis));
    }

    @Test
    void deletePersonShouldDecreaseListSize() {
        int sizeBefore = medicalRecordsRepository.getAllMedicalRecord().size();
        FullNameDto peterFullName = new FullNameDto ("Peter", "Duncan");

        medicalRecordService.deleteMedicalRecord(peterFullName);
        assertThat(medicalRecordsRepository.getAllMedicalRecord()).hasSize(sizeBefore - 1);
    }

    @Test
    void deleteNonExistingPersonShouldThrow() {
        FullNameDto fullName = new FullNameDto ("Zinedine", "Zidane");
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> medicalRecordService.deleteMedicalRecord(fullName));
    }
}
