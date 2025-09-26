package com.safetynet.api.controller.dto;

/**
 * Child summary used in child-alert responses.
 *
 * @param firstName given name
 * @param lastName  family name
 * @param age       completed years at the time of computation
 */
public record ChildDto(String firstName, String lastName, int age) {}