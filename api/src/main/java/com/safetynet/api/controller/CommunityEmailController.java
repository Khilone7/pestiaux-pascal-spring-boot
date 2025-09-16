package com.safetynet.api.controller;

import com.safetynet.api.service.CommunityEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommunityEmailController {

    private final CommunityEmailService communityEmailService;

    @GetMapping("/communityEmail")
    public List<String> getAllEmailByCity(@RequestParam("city") String email){
        return communityEmailService.getAllEmailByCity(email);
    }
}
