package com.safetynet.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record StationDto(List<PersonDto> personList, Long child, Long adult){}