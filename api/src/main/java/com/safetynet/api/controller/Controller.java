package com.safetynet.api.controller;

import com.safetynet.api.dto.FireStationDto;
import com.safetynet.api.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Controller {

    @Autowired
    FireStationService fireStationService;

    @GetMapping("/firestation/{station}")
    public FireStationDto.StationDto getPersonByStationAndChildCount(@PathVariable byte station){
        return fireStationService.urlNumeroUN(station);

    }
}
