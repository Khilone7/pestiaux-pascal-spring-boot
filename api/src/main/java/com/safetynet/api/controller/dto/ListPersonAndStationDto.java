package com.safetynet.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Residents living at a given address together with the covering fire station number.
 * <p>
 * Serialized with {@code NON_EMPTY}: empty lists are omitted from the JSON output.
 * </p>
 *
 * @param listPerson   list of residents with medical details
 * @param stationNumber fire station number covering the address
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ListPersonAndStationDto (List<PersonAndMedicationDto> listPerson, Long stationNumber){}