package com.safetynet.api.controller;

import com.safetynet.api.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private PersonService personService;

    @Test
    void addPerson_returns201() throws Exception {
        String body = """
        {
          "firstName":"John","lastName":"Doe",
          "address":"1509 Culver St","city":"Culver","zip":"97451",
          "phone":"841-874-6512","email":"john.doe@email.com"
        }
        """;

        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated());
    }

    @Test
    void updatePerson_returns204() throws Exception {
        String body = """
        {
          "firstName":"John","lastName":"Doe",
          "address":"New Addr","city":"Culver","zip":"97451",
          "phone":"000-000-0000","email":"john.doe@email.com"
        }
        """;

        mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletePerson_returns204() throws Exception {
        String body = """
        { "firstName":"John", "lastName":"Doe" }
        """;

        mockMvc.perform(delete("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isNoContent());
    }
}
