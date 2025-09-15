package com.safetynet.api.controller;

import com.safetynet.api.controller.dto.HouseholdDto;
import com.safetynet.api.service.FloodStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FloodStationController {

    private final FloodStationService floodStationService;

    @GetMapping("flood/stations")
    public List<HouseholdDto> getListResidentAndMedicationByAddresses (@RequestParam("stations") List<Long> stationNumbers){
        return floodStationService.getListResidentAndMedicationByAddresses(stationNumbers);
    }
}
