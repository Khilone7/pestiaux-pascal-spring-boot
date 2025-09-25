package com.safetynet.api.controller;

import com.safetynet.api.service.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicalRecordController.class)
class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MedicalRecordService medicalRecordService;

    @Test
    void addMedicalRecord_returns201() throws Exception {
        String body = """
        {
          "firstName":"John","lastName":"Doe",
          "birthdate":"01/01/1990",
          "medications":["aznol:350mg","hydrapermazol:100mg"],
          "allergies":["nillacilan"]
        }
        """;

        mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated());
    }

    @Test
    void updateMedicalRecord_returns204() throws Exception {
        String body = """
        {
          "firstName":"John","lastName":"Doe",
          "birthdate":"01/01/1990",
          "medications":["aznol:350mg"],
          "allergies":[]
        }
        """;

        mockMvc.perform(put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteMedicalRecord_returns204() throws Exception {
        String body = """
        { "firstName":"John", "lastName":"Doe" }
        """;

        mockMvc.perform(delete("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isNoContent());
    }
}