package com.safetynet.api.controller.dto;

import java.util.Optional;

/**
 * Request body used to delete a fire-station mapping.
 * <p>
 * Exactly one of the two optional fields must be present:
 * either an {@code address} or a {@code station} number.
 * Supplying both or none will trigger an {@link IllegalArgumentException}
 * in the controller.
 * </p>
 *
 * @param address optional street address of the mapping to delete
 * @param station optional station number of the mapping to delete
 */
public record DeleteFireStationDto(Optional<String> address, Optional<Long> station) { }