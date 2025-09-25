package com.safetynet.api.controller;

import com.safetynet.api.controller.dto.*;
import com.safetynet.api.service.FireService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FireController.class)
class FireControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private FireService fireService;

    @Test
    void getPersonAndStationNumberByAddressReturn200AndExpectedStructure() throws Exception {
        ListPersonAndStationDto dto = new ListPersonAndStationDto(
                List.of(new PersonAndMedicationDto("Alice", "777", 7, List.of(), List.of())),
                8L);
        given(fireService.getPersonAndStationByAddress("Paris")).willReturn(dto);

        mockMvc.perform(get("/fire").param("address", "Paris")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.listPerson.length()").value(1))
                .andExpect(jsonPath("$.stationNumber").value(8));
    }

    @Test
    void getPersonAndStationNumberByAddressReturn200AndEmptyJsonWhenNotFound() throws Exception {
        given(fireService.getPersonAndStationByAddress("ZZ"))
                .willReturn(new ListPersonAndStationDto(List.of(), 0L));

        mockMvc.perform(get("/fire").param("address", "ZZ")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }
}
