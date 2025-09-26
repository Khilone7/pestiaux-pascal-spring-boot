package com.safetynet.api.controller.dto;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;

/**
 * Simple value object carrying a person's full name.
 * <p>
 * Used in request bodies (for example when deleting a
 * {@link Person} or a {@link MedicalRecord}) to identify the
 * record uniquely by its first and last name.
 * </p>
 *
 * @param firstName given name
 * @param lastName  family name
 */
public record FullNameDto(String firstName, String lastName) { }