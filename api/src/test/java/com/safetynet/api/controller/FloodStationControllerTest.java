package com.safetynet.api.controller;

import com.safetynet.api.controller.dto.ResidentDto;
import com.safetynet.api.service.FloodStationService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebMvcTest(controllers = FloodStationController.class)
class FloodStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FloodStationService floodStationService;

    @Test
    void getFloodStationsReturns200AndExpectedStructure() throws Exception {
        Map<String, List<ResidentDto>> dto = new HashMap<>();
        dto.put("1509 Culver St", List.of(new ResidentDto("John Boyd", "841-874-6512", 36, List.of("aznol:350mg"), List.of("nillacilan"))
        ));

        given(floodStationService.getListResidentAndAddressAndMedicationByStation(List.of(1L, 2L)))
                .willReturn(dto);

        mockMvc.perform(get("/flood/stations").param("stations", "1", "2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['1509 Culver St']").exists())
                .andExpect(jsonPath("$['1509 Culver St'].length()").value(1));
    }

    @Test
    void getFloodStationsReturns200AndEmptyJsonWhenNotFound() throws Exception {
        given(floodStationService.getListResidentAndAddressAndMedicationByStation(List.of(99L)))
                .willReturn(Collections.emptyMap());

        mockMvc.perform(get("/flood/stations").param("stations", "99")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }
}