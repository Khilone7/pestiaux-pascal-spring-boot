package com.safetynet.api.controller.dto;

import java.util.List;

/**
 * Person summary filtered by last name, including contact and medical details.
 *
 * @param lastName    family name
 * @param address     street address
 * @param age         completed years at computation time
 * @param email       email address
 * @param medications list of medication strings
 * @param allergies   list of known allergies
 */
public record PersonInfoLastNameDto(String lastName, String address, int age, String email, List<String> medications, List<String> allergies) {}
