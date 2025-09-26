package com.safetynet.api.controller;

import com.safetynet.api.controller.dto.PersonInfoLastNameDto;
import com.safetynet.api.service.PersonInfoLastNameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST endpoint that returns detailed information (address, age,
 * email and medical details) for all persons with a given last name.
 * <p>
 * Delegates the lookup to {@link PersonInfoLastNameService}.
 * </p>
 */
@Log4j2
@RequiredArgsConstructor
@RestController
public class PersonInfoLastNameController {

    private final PersonInfoLastNameService personInfoLastNameService;

    /**
     * GET /personInfolastName={lastName}
     * <p>
     * Returns a list of persons whose last name matches the provided path
     * variable. Each entry includes address, age, email and medical information.
     * </p>
     *
     * @param lastName last name to query
     * @return list of {@link PersonInfoLastNameDto} objects with full details
     */
    @GetMapping("/personInfolastName={lastName}")
    public List<PersonInfoLastNameDto> getPersonAddressAndMedicationsByName(@PathVariable String lastName){
        log.info("REQUEST GET /personInfolastName={} ", lastName);

        List<PersonInfoLastNameDto> result = personInfoLastNameService.getPersonAddressAndMedicationsByName(lastName);

        log.info("RESPONSE GET /personInfolastName -> 200 OK | {} people information returned for last name {}",
                result.size(), lastName);
        return result;
    }
}