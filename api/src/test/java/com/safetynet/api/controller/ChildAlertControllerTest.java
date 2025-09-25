package com.safetynet.api.controller;

import com.safetynet.api.controller.dto.AdultDto;
import com.safetynet.api.controller.dto.ChildAlertResponseDto;
import com.safetynet.api.controller.dto.ChildDto;
import com.safetynet.api.service.ChildAlertService;
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

@WebMvcTest(controllers = ChildAlertController.class)
class ChildAlertControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ChildAlertService childAlertService;

    @Test
    void getChildByAddressReturns200AndExpectedStructure() throws Exception {
        ChildAlertResponseDto dto = new ChildAlertResponseDto(
                List.of(new ChildDto("Alice", "Dupont", 7)),
                List.of(new AdultDto("Bob", "Dujardin")));

        given(childAlertService.getChildByAddress("Paris")).willReturn(dto);

        mockMvc.perform(get("/childAlert").param("address", "Paris")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.childList.length()").value(1))
                .andExpect(jsonPath("$.adultList.length()").value(1));
    }

    @Test
    void getChildByAddressReturns200AndEmptyJsonWhenNotFound() throws Exception {
        given(childAlertService.getChildByAddress("ZZ")).willReturn(new ChildAlertResponseDto(List.of(), List.of()));

        mockMvc.perform(get("/childAlert").param("address", "ZZ")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }
}
