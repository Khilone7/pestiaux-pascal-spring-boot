package com.safetynet.api.controller;

import com.safetynet.api.controller.dto.ListPersonAndStationDto;
import com.safetynet.api.service.FireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class FireController {

    private final FireService fireService;

    @GetMapping("/fire")
    public ListPersonAndStationDto getListPersonAndStationByAddress(@RequestParam("address") String address) {
        log.info("REQUEST GET /fire?address={}", address);

        ListPersonAndStationDto result = fireService.getPersonAndStationByAddress(address);

        log.info("RESPONSE GET /fire -> 200 OK | {} people at address {} are served by station {}",
                result.listPerson().size(), address, result.stationNumber());
        return result;
    }
}
