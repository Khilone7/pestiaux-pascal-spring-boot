package com.safetynet.api.controller.dto;

import java.util.List;

public record StationDto(List<PersonDto> person, int child, int adult){}