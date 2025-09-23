package com.safetynet.api.controller.dto;

import java.util.List;

public record ListPersonAndStationDto (List<PersonAndMedicationDto> listPerson, Long stationNumber){}