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
public class ProjectsControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void validData_Should_Successfully_Add_Projects() throws Exception
    {
        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "projectName": "projectsTest",
                                  "siteAddressId": "address"
                                }
                                """))
                .andExpect(status().isOk());
    }


    @Test
    void blankProjectName_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "projectName": " ",
                          "siteAddressId": "address"
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
                                  "projectName": "Test",
                                  "siteAddressId": " "
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void nullProjectName_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "siteAddressId": "Address1"
                            }
                            """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void nullSiteAddressId_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "projectName": "Project"
                            }
                            """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void duplicateProjectName_Should_Return_Conflict() throws Exception
    {

        String request = """
            {
              "projectName": "DuplicateProject",
              "siteAddressId": "Address1"
            }
            """;

        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isConflict());
    }

    @Test
    void sameProjectName_DifferentCase_Should_Return_Conflict() throws Exception
    {
        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "projectName": "TestProject",
                              "siteAddressId": "Address1"
                            }
                            """))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "projectName": "testproject",
                              "siteAddressId": "address2"
                            }
                            """))
                .andExpect(status().isConflict());
    }
}
