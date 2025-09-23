package com.safetynet.api.controller;

import com.safetynet.api.controller.dto.DeleteFireStationDto;
import com.safetynet.api.controller.dto.StationDto;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.service.FireStationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/firestation")
public class FireStationController {

    private final FireStationService fireStationService;

    @GetMapping
    public StationDto getPersonByStationAndChildCount(@RequestParam("stationNumber") Long stationNumber){
        return fireStationService.getPersonsByStation(stationNumber);
    }

    @PostMapping
    public ResponseEntity<Void> addFireStation(@RequestBody FireStation fireStation){
        fireStationService.addFireStation(fireStation);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateFireStationNumber(@RequestBody FireStation fireStation){
        fireStationService.updateFireStationNumber(fireStation);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFireStation(@RequestBody DeleteFireStationDto fireStationDto){
        fireStationService.deleteFireStation(fireStationDto);
        return ResponseEntity.ok().build();
    }
}
