package com.safetynet.api.controller.dto;

import java.util.List;

public record PersonAndMedicationDto(String lastName, String phone, int age, List<String> medication, List<String> allergies){}