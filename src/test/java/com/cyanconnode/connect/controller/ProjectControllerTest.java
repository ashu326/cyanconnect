package com.cyanconnode.connect.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest
{
    @Autowired
    private MockMvc mockMvc;

        @Test
        void addProject_Should_Return_201_When_Valid_Request() throws Exception
        {

            mockMvc.perform(post("/api/v1/projects")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                {
                  "projectName":"ProjectA",
                  "addressLine1": "Sector 52",
                  "addressLine2": "Near Metro Station",
                  "city": "New Delhi",
                  "state": "Delhi",
                  "pinCode": 110096
                }
                """))
                    .andExpect(status().isCreated());
    }

    @Test
    void missingProjectName_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "addressLine1": "Sector 52",
                  "addressLine2": "Near Metro Station",
                  "city": "New Delhi",
                  "state": "Delhi",
                  "pinCode": 110096
                }
                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void missingSiteAddressId_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "projectName":"ProjectA"
                }
                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void blankProjectName_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "projectName":"",
                  "addressLine1": "Sector 52",
                  "addressLine2": "Near Metro Station",
                  "city": "New Delhi",
                  "state": "Delhi",
                  "pinCode": 110096
                }
                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void blankSiteAddressId_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "projectName":"ProjectB",
                  "addressLine1": "",
                  "addressLine2": "",
                  "city": "",
                  "state": "",
                  "pinCode": null
                }
                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void duplicateProjectName_Should_Return_Conflict() throws Exception
    {

        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "projectName":"ProjectA",
                      "addressLine1": "Sector 52",
                      "addressLine2": "Near Metro Station",
                      "city": "New Delhi",
                      "state": "Delhi",
                      "pinCode": 110096
                    }
                    """))
                .andExpect(status().isConflict());
    }
}
