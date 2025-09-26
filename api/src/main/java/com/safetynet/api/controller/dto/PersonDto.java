package com.safetynet.api.controller.dto;

/**
 * Minimal resident projection used by the fire-station endpoint.
 *
 * @param firstName given name
 * @param lastName  family name
 * @param address   street address
 * @param phone     phone number (no specific format enforced)
 */
public record PersonDto (String firstName, String lastName, String address, String phone){}