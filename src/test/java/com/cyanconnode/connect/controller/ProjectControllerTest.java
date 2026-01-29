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
public class ProjectControllerTest
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
    public void getProjects_withoutLimit_returnsBadRequest() throws Exception
    {
        mockMvc.perform(
                        get("/api/v1/projects")
                                .param("offset", "0")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getProjects_withoutOffset_returnsBadRequest() throws Exception
    {
        mockMvc.perform(
                        get("/api/v1/projects")
                                .param("limit", "10")
                )
                .andExpect(status().isBadRequest());
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

}
