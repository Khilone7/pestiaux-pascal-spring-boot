package com.safetynet.api.controller.dto;

import java.util.List;

public class FireStationDto {

    public record StationDto(List<PersonDto>person,int child, int adult){}

    public record PersonDto (String firstName, String lastName, String address, String phone){}

}
