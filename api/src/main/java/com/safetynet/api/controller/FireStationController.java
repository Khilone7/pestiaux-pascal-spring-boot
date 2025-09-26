package com.safetynet.api.controller;

import com.safetynet.api.controller.dto.DeleteFireStationDto;
import com.safetynet.api.controller.dto.StationDto;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.service.FireStationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

/**
 * REST controller exposing CRUD operations and resident listing
 * for fire station mappings.
 * <p>
 * Base path: <strong>/firestation</strong>.
 * Delegates all processing to {@link FireStationService}.
 * </p>
 */
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/firestation")
public class FireStationController {

    private final FireStationService fireStationService;

    /**
     * GET /firestation
     * <p>
     * Returns the list of residents served by the specified station number,
     * together with the number of children (age ≤ 18) and adults (age > 18).
     * </p>
     *
     * @param stationNumber fire station number to query
     * @return a {@link StationDto} with residents and child/adult counts
     */
    @GetMapping
    public StationDto getPersonByStationAndChildCount(@RequestParam("stationNumber") Long stationNumber) {
        log.info("REQUEST GET /firestation?stationNumber={}", stationNumber);
        StationDto result = fireStationService.getPersonsByStation(stationNumber);
        log.info("RESPONSE GET /firestation -> 200 OK | {} people ({} children, {} adults) are served by station {}",
                result.personList().size(), result.child(), result.adult(), stationNumber);
        return result;
    }

    /**
     * POST /firestation
     * <p>
     * Creates a new fire station mapping.
     * </p>
     *
     * @param fireStation fire station mapping to create
     * @return HTTP 201 (Created) if the mapping is successfully created
     * @throws IllegalStateException if a mapping with the same address and station already exists
     */
    @PostMapping
    public ResponseEntity<Void> addFireStation(@RequestBody FireStation fireStation) {
        log.info("REQUEST POST /firestation");
        fireStationService.addFireStation(fireStation);
        ResponseEntity<Void> response = ResponseEntity.status(201).build();
        log.info("RESPONSE POST /firestation -> {} | {} successfully created", response.getStatusCode(), fireStation);
        return response;
    }

    /**
     * PUT /firestation
     * <p>
     * Updates the station number for an existing address mapping.
     * </p>
     *
     * @param fireStation object containing the address and the new station number
     * @return HTTP 204 (No Content) if the update is successful
     * @throws NoSuchElementException if no mapping exists for the given address
     */
    @PutMapping
    public ResponseEntity<Void> updateFireStationNumber(@RequestBody FireStation fireStation) {
        log.info("REQUEST PUT /firestation");
        fireStationService.updateFireStationNumber(fireStation);
        ResponseEntity<Void> response = ResponseEntity.noContent().build(); // 204
        log.info("RESPONSE PUT /firestation -> {} | {} successfully updated", response.getStatusCode(), fireStation);
        return response;
    }

    /**
     * DELETE /firestation
     * <p>
     * Deletes a fire station mapping by either address or station number.
     * Exactly one of the two optional fields must be provided in the request body.
     * </p>
     *
     * @param fireStationDto DTO carrying either an address or a station number
     * @return HTTP 204 (No Content) if the deletion is successful
     * @throws IllegalArgumentException if both or neither of the optional fields are provided
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteFireStation(@RequestBody DeleteFireStationDto fireStationDto) {
        log.info("REQUEST DELETE /firestation");
        fireStationService.deleteFireStation(fireStationDto);
        ResponseEntity<Void> response = ResponseEntity.noContent().build(); // 204
        log.info("RESPONSE DELETE /firestation -> {} | firestation successfully deleted", response.getStatusCode());
        return response;
    }
}
