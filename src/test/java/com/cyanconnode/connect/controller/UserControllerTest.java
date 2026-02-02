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
class UserControllerTest
{

    @Autowired
    private MockMvc mockMvc;

    @Test
    void validData_Should_Successfully_Create_User() throws Exception
    {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name":"test2",
                                  "userName":"test1234",
                                  "email":"test2@gmail.com",
                                  "phoneNo":9652444555,
                                  "password":"test123"
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void missingEmail_Should_Return_BadRequest() throws Exception
    {
        String unique = "user" + System.currentTimeMillis();
        String uniqueEmail = unique + "@gmail.com";

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {
              "name":"user1",
              "userName":"user1234",
              "email":"user1@gmail.com",
              "phoneNo":9652448555,
              "password":"user1234"
            }
            """.formatted(unique, uniqueEmail)))
                .andExpect(status().isOk());
    }

    @Test
    void invalidEmail_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name":"test",
                          "userName":"test123",
                          "email":"invalid-email",
                          "phoneNo":"9876543210",
                          "password":"test123"
                        }
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void blankName_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name":"",
                          "userName":"test123",
                          "email":"test@gmail.com",
                          "phoneNo":"9876543210",
                          "password":"test123"
                        }
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalidPhoneNumber_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name":"test",
                          "userName":"test123",
                          "email":"test@gmail.com",
                          "phoneNo":"12345",
                          "password":"test123"
                        }
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void blankPassword_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name":"test",
                          "userName":"test123",
                          "email":"test@gmail.com",
                          "phoneNo":"9876543210",
                          "password":""
                        }
                        """))
                .andExpect(status().isBadRequest());
    }
}