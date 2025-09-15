package com.safetynet.api.controller.dto;

import java.util.List;

public record HouseholdDto(String address, List<ResidentDto> residents) {
}
