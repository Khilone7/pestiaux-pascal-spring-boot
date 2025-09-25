package com.safetynet.api.controller;

import com.safetynet.api.controller.dto.PersonInfoLastNameDto;
import com.safetynet.api.service.PersonInfoLastNameService;
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

@WebMvcTest(controllers = PersonInfoLastNameController.class)
public class PersonInfoLastNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonInfoLastNameService personInfoLastNameService;

    @Test
    void getPersonInfoByLastNameReturns200AndExpectedStructure() throws Exception {
        List<PersonInfoLastNameDto> dto = List.of(
                new PersonInfoLastNameDto("Boyd", "1509 Culver St", 36, "john.boyd@example.com", List.of("aznol:350mg"), List.of("nillacilan")));

        given(personInfoLastNameService.getPersonAddressAndMedicationsByName("Boyd"))
                .willReturn(dto);

        mockMvc.perform(get("/personInfolastName=Boyd")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].lastName").value("Boyd"));
    }

    @Test
    void getPersonInfoByLastNameReturns200AndEmptyListWhenNotFound() throws Exception {
        given(personInfoLastNameService.getPersonAddressAndMedicationsByName("ZZ"))
                .willReturn(List.of()); // liste vide

        mockMvc.perform(get("/personInfolastName=ZZ")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
