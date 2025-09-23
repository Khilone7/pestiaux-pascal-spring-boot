package com.safetynet.api.controller;

import com.safetynet.api.controller.dto.DeleteFireStationDto;
import com.safetynet.api.controller.dto.StationDto;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.service.FireStationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/firestation")
public class FireStationController {

    private final FireStationService fireStationService;

    @GetMapping
    public StationDto getPersonByStationAndChildCount(@RequestParam("stationNumber") Long stationNumber) {
        log.info("REQUEST GET /firestation?stationNumber={}", stationNumber);
        StationDto result = fireStationService.getPersonsByStation(stationNumber);
        log.info("RESPONSE GET /firestation -> 200 OK | {} people ({} children, {} adults) are served by station {}",
                result.personList().size(), result.child(), result.adult(), stationNumber);
        return result;
    }

    @PostMapping
    public ResponseEntity<Void> addFireStation(@RequestBody FireStation fireStation) {
        log.info("REQUEST POST /firestation");
        fireStationService.addFireStation(fireStation);
        ResponseEntity<Void> response = ResponseEntity.status(201).build();
        log.info("RESPONSE POST /firestation -> {} | {} successfully created", response.getStatusCode(), fireStation);
        return response;
    }

    @PutMapping
    public ResponseEntity<Void> updateFireStationNumber(@RequestBody FireStation fireStation) {
        log.info("REQUEST PUT /firestation");
        fireStationService.updateFireStationNumber(fireStation);
        ResponseEntity<Void> response = ResponseEntity.noContent().build(); // 204
        log.info("RESPONSE PUT /firestation -> {} | {} successfully updated", response.getStatusCode(), fireStation);
        return response;
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFireStation(@RequestBody DeleteFireStationDto fireStationDto) {
        log.info("REQUEST DELETE /firestation");
        fireStationService.deleteFireStation(fireStationDto);
        ResponseEntity<Void> response = ResponseEntity.noContent().build(); // 204
        log.info("RESPONSE DELETE /firestation -> {} | firestation successfully deleted", response.getStatusCode());
        return response;
    }
}
