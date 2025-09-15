package com.safetynet.api.controller;

import com.safetynet.api.controller.dto.PersonInfoLastNameDto;
import com.safetynet.api.service.PersonInfoLastNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PersonInfoLastNameController {

    private final PersonInfoLastNameService personInfoLastNameService;

    @GetMapping("/personInfolastName={lastName}")
    public List<PersonInfoLastNameDto> getPersonAddressAndMedicationsByName(@PathVariable String lastName){
        return personInfoLastNameService.getPersonAddressAndMedicationsByName(lastName);
    }
}