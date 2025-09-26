package com.safetynet.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Residents covered by a fire station, with child/adult counts.
 * <p>
 * Serialized with {@code NON_EMPTY}: empty lists are omitted from the JSON output.
 * </p>
 *
 * @param personList list of residents (first/last name, address, phone)
 * @param child      number of children (age ≤ 18) among {@code personList}; may be omitted in JSON if empty
 * @param adult      number of adults (age > 18) among {@code personList}; may be omitted in JSON if empty
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record StationDto(List<PersonDto> personList, Long child, Long adult){}