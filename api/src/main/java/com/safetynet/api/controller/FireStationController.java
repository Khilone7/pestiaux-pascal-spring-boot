package com.safetynet.api.controller;

import com.safetynet.api.controller.dto.FireStationDto;
import com.safetynet.api.service.FireStationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
public class FireStationController {

    private final FireStationService fireStationService;

    @GetMapping("/firestation")
    public FireStationDto.StationDto getPersonByStationAndChildCount(@RequestParam("stationNumber") Long stationNumber){
        return fireStationService.getPersonsByStation(stationNumber);
    }
}
