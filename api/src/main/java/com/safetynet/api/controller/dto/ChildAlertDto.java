package com.safetynet.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class ChildAlertDto {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public record ListChildDto(List<ChildDto> child, List<AdultDto> adult) {}

    public record ChildDto(String firstName, String lastName, int age) {}

    public record AdultDto(String firstName, String lastName) {}
}
