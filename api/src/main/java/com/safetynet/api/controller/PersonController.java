package com.safetynet.api.controller;

import com.safetynet.api.controller.dto.FullNameDto;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

/**
 * REST controller exposing CRUD operations for {@link Person} entries.
 * <p>
 * Base path: <strong>/person</strong>.
 * Delegates all operations to {@link PersonService}.
 * </p>
 */
@Log4j2
@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    /**
     * POST /person
     * <p>
     * Creates a new person record.
     * </p>
     *
     * @param person person to create
     * @return HTTP 201 (Created) if the person is successfully created
     * @throws IllegalStateException if a person with the same first and last name already exists
     */
    @PostMapping
    public ResponseEntity<Void> addPerson(@RequestBody Person person) {
        log.info("REQUEST POST /person");
        personService.addPerson(person);
        ResponseEntity<Void> response = ResponseEntity.status(201).build();
        log.info("RESPONSE POST /person -> {} | {} successfully created", response.getStatusCode(), person);
        return response;
    }

    /**
     * PUT /person
     * <p>
     * Updates the record of a person that matches the given first and last name.
     * </p>
     *
     * @param person person containing the updated information
     * @return HTTP 204 (No Content) if the update is successful
     * @throws NoSuchElementException if no person with the given names exists
     */
    @PutMapping
    public ResponseEntity<Void> updatePerson(@RequestBody Person person) {
        log.info("REQUEST PUT /person");
        personService.updatePerson(person);
        ResponseEntity<Void> response = ResponseEntity.noContent().build();
        log.info("RESPONSE PUT /person -> {} | {} successfully updated", response.getStatusCode(), person);
        return response;
    }

    /**
     * DELETE /person
     * <p>
     * Deletes the person record identified by the specified first and last name.
     * </p>
     *
     * @param fullName DTO carrying the first and last name of the person to delete
     * @return HTTP 204 (No Content) if the deletion is successful
     * @throws NoSuchElementException if no person with the given names exists
     */
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
