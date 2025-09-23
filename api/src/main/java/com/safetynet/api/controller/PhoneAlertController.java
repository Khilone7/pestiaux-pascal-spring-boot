package com.safetynet.api.controller;

import com.safetynet.api.service.PhoneAlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
public class PhoneAlertController {

    private final PhoneAlertService phoneAlertService;

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
