package com.safetynet.api.controller.dto;

import java.util.List;

public record ResidentDto(String name, String phone, int age, List<String> medications, List<String> allergies) {
}
