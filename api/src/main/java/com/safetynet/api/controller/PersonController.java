package com.safetynet.api.controller;

import com.safetynet.api.controller.dto.DeletePersonDto;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping
    public ResponseEntity<Void> addPerson(@RequestBody Person person){
        personService.addPerson(person);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updatePerson(@RequestBody Person person){
        personService.updatePerson(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePerson(@RequestBody DeletePersonDto fullName){
        personService.deletePerson(fullName);
        return ResponseEntity.ok().build();
    }
}
