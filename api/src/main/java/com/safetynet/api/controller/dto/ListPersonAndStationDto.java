package com.safetynet.api.controller.dto;

import java.util.List;
import java.util.Optional;

public record ListPersonAndStationDto (List<PersonAndMedicationDto> listPerson, Optional<Long> stationNumber){}