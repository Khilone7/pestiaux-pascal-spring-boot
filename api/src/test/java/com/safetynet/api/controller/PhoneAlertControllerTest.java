package com.safetynet.api.controller;

import com.safetynet.api.service.PhoneAlertService;
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

import java.util.List;

@WebMvcTest(controllers = PhoneAlertController.class)
class PhoneAlertControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private PhoneAlertService phoneAlertService;

    @Test
    void getPhoneByStationReturns200AndExpectedStructure() throws Exception {
        given(phoneAlertService.getPhoneByStation(3L)).willReturn(List.of("841-874-6512", "841-874-8547"));

        mockMvc.perform(get("/phoneAlert").param("firestation", "3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0]").value("841-874-6512"))
                .andExpect(jsonPath("$[1]").value("841-874-8547"));
    }

    @Test
    void getPhoneByStationReturns200AndEmptyListWhenNotFound() throws Exception {
        given(phoneAlertService.getPhoneByStation(99L)).willReturn(List.of());

        mockMvc.perform(get("/phoneAlert").param("firestation", "99")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}