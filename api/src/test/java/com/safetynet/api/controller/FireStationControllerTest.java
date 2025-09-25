package com.safetynet.api.controller;

import com.safetynet.api.controller.dto.PersonDto;
import com.safetynet.api.controller.dto.StationDto;
import com.safetynet.api.service.FireStationService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.BDDMockito.given;

@WebMvcTest(controllers = FireStationController.class)
class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FireStationService fireStationService;

    @Test
    void getPersonsByStationReturns200AndExpectedStructure() throws Exception {
        StationDto dto = new StationDto(
                List.of(new PersonDto("John", "Doe", "1509 Culver St", "841-874-6512")),
                1L,
                2L
        );

        given(fireStationService.getPersonsByStation(1L)).willReturn(dto);

        mockMvc.perform(get("/firestation").param("stationNumber", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personList").exists())
                .andExpect(jsonPath("$.child").value(1))
                .andExpect(jsonPath("$.adult").value(2));
    }

    @Test
    void getPersonsByStationReturns200AndEmptyJsonWhenNotFound() throws Exception {
        given(fireStationService.getPersonsByStation(999L))
                .willReturn(new StationDto(List.of(), null, null));

        mockMvc.perform(get("/firestation").param("stationNumber", "999")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    void addFireStation_returns201() throws Exception {
        String body = """
        { "address":"1509 Culver St", "station":3 }
        """;

        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());
    }

    @Test
    void updateFireStation_returns204() throws Exception {
        String body = """
        { "address":"1509 Culver St", "station":4 }
        """;

        mockMvc.perform(put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteFireStation_returns204() throws Exception {
        String body = """
        { "address":"1509 Culver St", "station":4 }
        """;

        mockMvc.perform(delete("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNoContent());
    }
}
