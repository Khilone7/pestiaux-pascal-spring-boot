package com.safetynet.api.controller.dto;

import java.util.List;

public class FireDto {

    public record ListPersonAndStationDto (List<PersonDto> listPerson, Long stationNumber){}

    public record PersonDto (String lastName, String phone, Long age, List<String> medication, List<String> allergies){}
}
