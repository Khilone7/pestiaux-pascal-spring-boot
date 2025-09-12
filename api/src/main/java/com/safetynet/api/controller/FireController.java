package com.safetynet.api.controller;


import com.safetynet.api.controller.dto.ListPersonAndStationDto;
import com.safetynet.api.service.FireService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FireController {

    private final FireService fireService;

    @GetMapping("/fire")
    public ListPersonAndStationDto getListPersonAndStationByAddress (@RequestParam("address") String address){
        return fireService.getPersonAndStationByAddress(address);
    }
}
