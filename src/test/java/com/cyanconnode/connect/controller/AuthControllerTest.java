package com.cyanconnode.connect.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest
{
    @Autowired
    private MockMvc mockMvc;


    @Test
    void loginUser_Should_Return_Token_When_Valid_Credentials() throws Exception
    {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "email":"test@gmail.com",
                  "password": "12345"
                }
                """))
                .andExpect(status().isOk()).andExpect(content().
                        contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void missingEmail_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "password": "12345"
                }
                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void missingPassword_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "email":"test@gmail.com"
                }
                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginUser_Should_Return_BadRequest_When_Email_Is_Null() throws Exception
    {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "email":"",
                  "password": "12345"
                }
                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginUser_Should_Return_BadRequest_When_Password_Is_Null() throws Exception
    {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "email":"test@gmail.com",
                  "password": ""
                }
                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalidEmail_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "email":"testgmail.com",
                  "password": "12345"
                }
                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalidPassword_Should_Return_Unauthorized() throws Exception
    {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "email":"test@gmail.com",
                  "password": "1234"
                }
                """))
                .andExpect(status().isUnauthorized());
    }
}
