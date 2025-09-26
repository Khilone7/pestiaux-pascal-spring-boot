package com.safetynet.api.controller;

import com.safetynet.api.service.CommunityEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST endpoint that returns the email addresses of all residents
 * in a specified city.
 * <p>
 * Delegates the lookup to {@link CommunityEmailService}.
 * </p>
 */
@Log4j2
@RequiredArgsConstructor
@RestController
public class CommunityEmailController {

    private final CommunityEmailService communityEmailService;

    /**
     * GET /communityEmail
     * <p>
     * Returns the list of email addresses of residents whose city
     * exactly matches the value provided as request parameter.
     * </p>
     *
     * @param city name of the city to query
     * @return list of email addresses of residents living in the specified city
     */
    @GetMapping("/communityEmail")
    public List<String> getAllEmailByCity(@RequestParam("city") String city){
        log.info("REQUEST GET /communityEmail?city={}", city);

        List<String> emails = communityEmailService.getEmailByCity(city);

        log.info("RESPONSE GET /communityEmail -> 200 OK | {} emails in city {}", emails.size(), city);
        return emails;
    }
}
