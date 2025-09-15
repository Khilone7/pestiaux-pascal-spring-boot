package com.safetynet.api.controller.dto;

import java.util.List;

public record PersonInfoLastNameDto(String lastName, String address, int age, String email, List<String> medications, List<String> allergies) {}
