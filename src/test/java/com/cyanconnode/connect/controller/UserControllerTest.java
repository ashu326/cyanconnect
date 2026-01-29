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
public class UserControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getUsers_Using_Offset_And_Limit() throws Exception
    {
        mockMvc.perform(
                        get("/api/v1/users")
                                .param("offset", "0")
                                .param("limit", "10")
                )
                .andExpect(status().isOk());
    }

    @Test
    public void getUsers_withoutLimit_returnsBadRequest() throws Exception
    {
        mockMvc.perform(
                        get("/api/v1/users")
                                .param("offset", "0")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getUsers_withoutOffset_returnsBadRequest() throws Exception
    {
        mockMvc.perform(
                        get("/api/v1/users")
                                .param("limit", "10")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getUsers_withStringOffset_returnsBadRequest() throws Exception
    {
        mockMvc.perform(
                        get("/api/v1/users")
                                .param("offset", "abc")
                                .param("limit", "10")
                )
                .andExpect(status().isBadRequest());
    }

    //Get User By Name Test
    @Test
    public void searchUsers_withName_returnsOk() throws Exception
    {
        mockMvc.perform(get("/api/v1/users/search")
                        .param("name", "user")
                        .param("offset", "0")
                        .param("limit", "5"))
                .andExpect(status().isOk());
    }

    @Test
    public void searchUsers_withoutName_returnsBadRequest() throws Exception
    {
        mockMvc.perform(get("/api/v1/users/search")
                        .param("offset", "0")
                        .param("limit", "5"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void searchUsers_withoutOffset_returnsBadRequest() throws Exception
    {
        mockMvc.perform(get("/api/v1/users/search")
                        .param("name", "user")
                        .param("limit", "5"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void searchUsers_withoutLimit_returnsBadRequest() throws Exception
    {
        mockMvc.perform(get("/api/v1/users/search")
                        .param("name", "user")
                        .param("offset", "0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void searchUsers_withStringOffset_returnsBadRequest() throws Exception
    {
        mockMvc.perform(get("/api/v1/users/search")
                        .param("name", "user")
                        .param("offset", "abc")
                        .param("limit", "5"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void searchUsers_noMatchingUsers_returnsOkWithEmptyList() throws Exception
    {
        mockMvc.perform(get("/api/v1/users/search")
                        .param("name", "zzzzzz")
                        .param("offset", "0")
                        .param("limit", "5"))
                .andExpect(status().isOk());
    }


}
