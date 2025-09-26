package com.safetynet.api.controller.dto;

import java.util.List;

/**
 * Resident summary with contact and medical information used by the fire endpoint.
 *
 * @param lastName   resident's family name
 * @param phone      resident's phone number
 * @param age        completed years at computation time
 * @param medication list of medication strings (e.g., drug name + dosage)
 * @param allergies  list of known allergies
 */
public record PersonAndMedicationDto(String lastName, String phone, int age, List<String> medication, List<String> allergies){}