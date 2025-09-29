package com.safetynet.api.Integration;

import com.safetynet.api.controller.dto.DeleteFireStationDto;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.repository.FireStationRepository;
import com.safetynet.api.service.FireStationService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class FireStationServiceIT {

    @Autowired
    FireStationRepository fireStationRepository;
    @Autowired
    FireStationService fireStationService;

    FireStation newFireStation, existingFireStation, nonExistingFireStation;

    private static final Path originalFile = Path.of("src/main/resources/Data.json");
    private static final Path backupFile = Path.of("src/test/resources/DataBackup.json");

    @BeforeAll
    static void backupJson() throws Exception {
        Files.copy(originalFile, backupFile, StandardCopyOption.REPLACE_EXISTING);
    }

    @AfterAll
    static void restoreJson() throws Exception {
        Files.copy(backupFile, originalFile, StandardCopyOption.REPLACE_EXISTING);
    }

    @BeforeEach
    void setUp() {
        newFireStation = FireStation.builder().station(1L).address("24 rue de l'église").build();
        existingFireStation = FireStation.builder().station(3L).address("1509 Culver St").build();
        nonExistingFireStation = FireStation.builder().station(7L).address("Poudlard").build();
    }

    @Test
    void addFireStationShouldIncreaseListSize() {
        int sizeBefore = fireStationRepository.getAllFireStation().size();

        fireStationService.addFireStation(newFireStation);
        assertThat(fireStationRepository.getAllFireStation()).hasSize(sizeBefore + 1);
    }

    @Test
    void addExistingFireStationShouldThrow() {
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> fireStationService.addFireStation(existingFireStation));
    }

    @Test
    void updateFireStationShouldModifyStation() {
        int sizeBefore = fireStationRepository.getAllFireStation().size();
        existingFireStation.setStation(9L);

        fireStationService.updateFireStationNumber(existingFireStation);
        assertThat(fireStationRepository.getAllFireStation()).hasSize(sizeBefore);
        assertTrue(fireStationRepository.getAllFireStation()
                .stream()
                .anyMatch(f -> f.getAddress().equals("1509 Culver St")
                        && f.getStation().equals(9L)));
    }

    @Test
    void updateNonExistingFireStationShouldThrow() {
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> fireStationService.updateFireStationNumber(nonExistingFireStation));
    }

    @Test
    void deleteFireStationShouldDecreaseListSize() {
        FireStation temporaryFireStation = FireStation.builder().station(123456L).address("Narnia").build();
        fireStationService.addFireStation(temporaryFireStation);

        int sizeBefore = fireStationRepository.getAllFireStation().size();
        DeleteFireStationDto stationAddress = new DeleteFireStationDto(Optional.of("834 Binoc Ave"),Optional.empty());
        DeleteFireStationDto stationNumber = new DeleteFireStationDto(Optional.empty(),Optional.of(123456L));

        fireStationService.deleteFireStation(stationAddress);
        fireStationService.deleteFireStation(stationNumber);
        assertThat(fireStationRepository.getAllFireStation()).hasSize(sizeBefore - 2);
    }

    @Test
    void deleteNonExistingFireStationShouldThrow() {
        DeleteFireStationDto nonExistingFireStation2 = new DeleteFireStationDto(Optional.of("Nul part"),Optional.of(8L));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> fireStationService.deleteFireStation(nonExistingFireStation2));
    }

    @Test
    void deleteExistingFireStationByAddressAndStationShouldThrow(){
        DeleteFireStationDto existingFireStation2 = new DeleteFireStationDto(Optional.of("1509 Culver St"),Optional.of(3L));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> fireStationService.deleteFireStation(existingFireStation2));
    }
}
