package com.safetynet.api.controller;

import com.safetynet.api.service.CommunityEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
public class CommunityEmailController {

    private final CommunityEmailService communityEmailService;

    @GetMapping("/communityEmail")
    public List<String> getAllEmailByCity(@RequestParam("city") String city){
        log.info("REQUEST GET /communityEmail?city={}", city);

        List<String> emails = communityEmailService.getEmailByCity(city);

        log.info("RESPONSE GET /communityEmail -> 200 OK | {} emails in city {}", emails.size(), city);
        return emails;
    }
}
