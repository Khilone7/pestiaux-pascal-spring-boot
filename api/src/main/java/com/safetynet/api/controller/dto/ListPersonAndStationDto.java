package com.safetynet.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ListPersonAndStationDto (List<PersonAndMedicationDto> listPerson, Long stationNumber){}