package com.safetynet.api.controller.dto;

/**
 * Adult summary used in child-alert responses.
 *
 * @param firstName given name
 * @param lastName  family name
 */
public record AdultDto(String firstName, String lastName) {}