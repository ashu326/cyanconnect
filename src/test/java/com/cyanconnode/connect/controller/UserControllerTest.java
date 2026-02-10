package com.cyanconnode.connect.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                .andExpect(status().isCreated());
    }

    @Test
    void missingEmail_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "name":"user1",
                              "userName":"user1234",
                              "phoneNo":"9652448555",
                              "password":"user1234"
                            }
                            """))
                .andExpect(status().isBadRequest());
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

    @Test
    void duplicateUser_Should_Return_Conflict() throws Exception
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
                .andExpect(status().isConflict());
    }

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
    public void getUsers_withStringOffset_returnsBadRequest() throws Exception
    {
        mockMvc.perform(
                        get("/api/v1/users")
                                .param("offset", "abc")
                                .param("limit", "10")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getUsers_withoutAnyParams_usesDefaultValues() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk());
    }


    //Get User By Name Test
    @Test
    public void searchUsers_withName_returnsOk() throws Exception
    {
        mockMvc.perform(get("/api/v1/users")
                        .param("name", "user")
                        .param("offset", "0")
                        .param("limit", "5"))
                .andExpect(status().isOk());
    }

    @Test
    public void getUsers_withoutName_returnsDefaultUsersList() throws Exception
    {
        mockMvc.perform(get("/api/v1/users")
                        .param("offset", "0")
                        .param("limit", "5"))
                .andExpect(status().isOk());
    }


    @Test
    public void searchUsers_onlyNameParam() throws Exception
    {
        mockMvc.perform(get("/api/v1/users")
                        .param("name", "user"))
                .andExpect(status().isOk());
    }

    @Test
    public void searchUsers_withStringOffset_returnsBadRequest() throws Exception
    {
        mockMvc.perform(get("/api/v1/users")
                        .param("name", "user")
                        .param("offset", "abc")
                        .param("limit", "5"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void searchUsers_noMatchingUsers_returnsOkWithEmptyList() throws Exception
    {
        mockMvc.perform(get("/api/v1/users")
                        .param("name", "zzzzzz")
                        .param("offset", "0")
                        .param("limit", "5"))
                .andExpect(status().isOk());
    }
}