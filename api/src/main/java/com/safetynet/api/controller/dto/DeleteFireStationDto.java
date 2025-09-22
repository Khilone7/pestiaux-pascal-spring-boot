package com.safetynet.api.controller.dto;

import java.util.Optional;

public record DeleteFireStationDto(Optional<String> address, Optional<Long> station) {
}
