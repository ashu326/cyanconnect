package com.cyanconnode.connect.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest
{

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getProjects_Using_Offset_And_Limit() throws Exception
    {
        mockMvc.perform(
                        get("/api/v1/projects")
                                .param("offset", "0")
                                .param("limit", "10")
                )
                .andExpect(status().isOk());
    }

    @Test
    public void getProjects_withStringOffset_returnsBadRequest() throws Exception
    {
        mockMvc.perform(
                        get("/api/v1/projects")
                                .param("offset", "abc")
                                .param("limit", "10")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getProjects_withoutAnyParams_usesDefaultValues() throws Exception {
        mockMvc.perform(get("/api/v1/projects"))
                .andExpect(status().isOk());
    }


    //Get Project By Name Test
    @Test
    public void searchProjects_withName_returnsOk() throws Exception
    {
        mockMvc.perform(get("/api/v1/projects")
                        .param("projectName", "project")
                        .param("offset", "0")
                        .param("limit", "5"))
                .andExpect(status().isOk());
    }

    @Test
    public void getProjects_withoutName_returnsDefaultUsersList() throws Exception
    {
        mockMvc.perform(get("/api/v1/projects")
                        .param("offset", "0")
                        .param("limit", "5"))
                .andExpect(status().isOk());
    }


    @Test
    public void searchProjects_onlyNameParam() throws Exception
    {
        mockMvc.perform(get("/api/v1/projects")
                        .param("projectName", "project"))
                .andExpect(status().isOk());
    }

    @Test
    public void searchProjects_withStringOffset_returnsBadRequest() throws Exception
    {
        mockMvc.perform(get("/api/v1/projects")
                        .param("projectName", "project")
                        .param("offset", "abc")
                        .param("limit", "5"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void searchProjects_noMatchingUsers_returnsOkWithEmptyList() throws Exception
    {
        mockMvc.perform(get("/api/v1/projects")
                        .param("projectName", "zzzzzz")
                        .param("offset", "0")
                        .param("limit", "5"))
                .andExpect(status().isOk());
    }
}