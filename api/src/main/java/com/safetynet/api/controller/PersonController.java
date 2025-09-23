package com.safetynet.api.controller;

import com.safetynet.api.controller.dto.FullNameDto;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping
    public ResponseEntity<Void> addPerson(@RequestBody Person person) {
        log.info("REQUEST POST /person");
        personService.addPerson(person);
        ResponseEntity<Void> response = ResponseEntity.status(201).build();
        log.info("RESPONSE POST /person -> {} | {} successfully created", response.getStatusCode(), person);
        return response;
    }

    @PutMapping
    public ResponseEntity<Void> updatePerson(@RequestBody Person person) {
        log.info("REQUEST PUT /person");
        personService.updatePerson(person);
        ResponseEntity<Void> response = ResponseEntity.noContent().build();
        log.info("RESPONSE PUT /person -> {} | {} successfully updated", response.getStatusCode(), person);
        return response;
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePerson(@RequestBody FullNameDto fullName) {
        log.info("REQUEST DELETE /person");
        personService.deletePerson(fullName);
        ResponseEntity<Void> response = ResponseEntity.noContent().build();
        log.info("RESPONSE DELETE /person -> {} | {} {} successfully deleted",
                response.getStatusCode(), fullName.firstName(), fullName.lastName());
        return response;
    }
}
