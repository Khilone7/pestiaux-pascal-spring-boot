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

/**
 * REST endpoint that returns, for a set of fire station numbers,
 * the residents grouped by address together with their medical details.
 * <p>
 * Delegates the processing to {@link FloodStationService}.
 * </p>
 */
@Log4j2
@RequiredArgsConstructor
@RestController
public class FloodStationController {

    private final FloodStationService floodStationService;

    /**
     * GET /flood/stations
     * <p>
     * For the given list of station numbers, returns a map where each key is
     * an address served by those stations and each value is the list of
     * {@link ResidentDto} objects representing the residents of that address,
     * including their age and medical information.
     * </p>
     *
     * @param stationNumbers list of fire station numbers to query
     * @return map of address → list of residents with medical details
     */
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
