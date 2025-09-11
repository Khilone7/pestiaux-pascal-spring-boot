package com.safetynet.api.controller;

import com.safetynet.api.service.ChildAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChildAlertController {

    private final ChildAlertService childAlertService;

    @GetMapping("/childAlert")
    public ChildAlertDto.ListChildDto getChildByAddress(@RequestParam("address") String address){
        return childAlertService.getChildByAddress(address);
    }
}