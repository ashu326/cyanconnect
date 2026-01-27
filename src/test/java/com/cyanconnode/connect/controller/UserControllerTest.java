package com.cyanconnode.connect.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getUser() throws Exception
    {
        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .get("/api/v1/users")
                                .param("page", "0")
                                .param("size", "5")
                )
                .andExpect(org.springframework.test.web.servlet.result
                        .MockMvcResultMatchers.status().isOk());
    }
}
