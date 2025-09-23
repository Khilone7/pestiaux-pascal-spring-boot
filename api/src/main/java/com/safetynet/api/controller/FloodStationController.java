package com.safetynet.api.controller;

import com.safetynet.api.controller.dto.ResidentDto;
import com.safetynet.api.service.FloodStationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@RestController
public class FloodStationController {

    private final FloodStationService floodStationService;

    @GetMapping("flood/stations")
    public Map<String,List<ResidentDto>> getListResidentAndAddressAndMedicationByStation (@RequestParam("stations") List<Long> stationNumbers){
        log.info("REQUEST GET /flood/stations?stations={}", stationNumbers);

        Map<String, List<ResidentDto>> result = floodStationService.getListResidentAndAddressAndMedicationByStation(stationNumbers);
        int totalResidents = result.values().stream().mapToInt(List::size).sum();
        int totalAddresses = result.size();

        log.info("RESPONSE GET /flood/stations -> 200 OK | {} residents across {} addresses for stations {}",
                totalResidents, totalAddresses, stationNumbers);
        return result;
    }
}
