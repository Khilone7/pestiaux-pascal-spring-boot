package com.safetynet.api.controller;

import com.safetynet.api.controller.dto.ChildAlertResponseDto;
import com.safetynet.api.service.ChildAlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class ChildAlertController {

    private final ChildAlertService childAlertService;

    @GetMapping("/childAlert")
    public ChildAlertResponseDto getChildByAddress(@RequestParam("address") String address) {
        log.info("REQUEST GET /childAlert?address={}", address);

        ChildAlertResponseDto response = childAlertService.getChildByAddress(address);
        int children = response.childList().size();
        int adults = response.adultList().size();

        log.info("RESPONSE GET /childAlert -> 200 OK | {} people ({} children and {} adults) at address {}",
                children + adults, children, adults, address);
        return response;
    }
}