package com.safetynet.api.controller;

import com.safetynet.api.service.CommunityEmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CommunityEmailController.class)
class CommunityEmailControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CommunityEmailService communityEmailService;

    @Test
    void getCommunityEmailReturns200AndExpectedStructure() throws Exception {
        List<String> emails = new ArrayList<>();
        emails.add("Jean@gmail.com");
        emails.add("Jacques@gmail.com");

        given(communityEmailService.getEmailByCity("Paris")).willReturn(emails);

        mockMvc.perform(get("/communityEmail").param("city", "Paris")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getChildByAddressReturns200AndEmptyJsonWhenNotFound() throws Exception {
        given(communityEmailService.getEmailByCity("ZZ"))
                .willReturn(List.of());

        mockMvc.perform(get("/communityEmail").param("city", "ZZ")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
