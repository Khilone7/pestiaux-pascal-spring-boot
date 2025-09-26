package com.safetynet.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * Child/Adult split for the persons living at a given address.
 * <p>
 * Serialized with {@code NON_EMPTY}: empty lists are omitted from the JSON output.
 * </p>
 *
 * @param childList list of children (age ≤ 18)
 * @param adultList list of adults (age > 18)
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ChildAlertResponseDto(List<ChildDto> childList, List<AdultDto> adultList) {
}