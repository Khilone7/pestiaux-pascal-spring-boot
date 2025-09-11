package com.safetynet.api.controller;

import com.safetynet.api.service.PhoneAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PhoneAlertController {

    private final PhoneAlertService phoneAlertService;

    @GetMapping("/phoneAlert")
    public List<String> getPhoneByStation(@RequestParam("firestation") Long fireStationNumber){
        return phoneAlertService.getPhoneByStation(fireStationNumber);
    }
}
