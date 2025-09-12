package com.safetynet.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ChildAlertResponseDto(List<ChildDto> childList, List<AdultDto> adultList) {
}