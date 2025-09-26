package com.safetynet.api.controller.dto;

import java.util.List;

/**
 * Resident summary grouped by address in the flood/station response.
 *
 * @param name        resident's last name
 * @param phone       phone number
 * @param age         completed years at computation time
 * @param medications list of medication strings
 * @param allergies   list of known allergies
 */
public record ResidentDto(String name, String phone, int age, List<String> medications, List<String> allergies) {
}
