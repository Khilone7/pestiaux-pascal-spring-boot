package com.safetynet.api.controller;

import com.safetynet.api.service.PhoneAlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST endpoint that returns the phone numbers of all residents served by
 * a specific fire station.
 * <p>
 * Delegates the processing to {@link PhoneAlertService}.
 * </p>
 */
@Log4j2
@RestController
@RequiredArgsConstructor
public class PhoneAlertController {

    private final PhoneAlertService phoneAlertService;

    /**
     * GET /phoneAlert
     * <p>
     * Returns the list of phone numbers for residents living at addresses
     * covered by the specified fire station.
     * </p>
     *
     * @param fireStationNumber fire station number to query (request parameter {@code firestation})
     * @return list of phone numbers of residents covered by the given fire station
     */
    @GetMapping("/phoneAlert")
    public List<String> getPhoneByStation(@RequestParam("firestation") Long fireStationNumber) {
        log.info("REQUEST GET /phoneAlert?firestation={}", fireStationNumber);

        List<String> phones = phoneAlertService.getPhoneByStation(fireStationNumber);
        Long addressCount = phoneAlertService.countAddressesByStation(fireStationNumber);

        log.info("RESPONSE GET /phoneAlert -> 200 OK | {} phone numbers across {} addresses for stations {}",
                phones.size(), addressCount, fireStationNumber);
        return phones;
    }
}
