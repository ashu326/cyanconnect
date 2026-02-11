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
    void missingSiteAddress_Should_Return_BadRequest() throws Exception
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
    void blankSiteAddress_Should_Return_BadRequest() throws Exception
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

    @Test
    void blankAddressLine1_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {
              "projectName":"ProjectC",
              "addressLine1": "",
              "addressLine2": "Near Metro",
              "city": "New Delhi",
              "state": "Delhi",
              "pinCode": 110096
            }
            """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void blankCity_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {
              "projectName":"ProjectD",
              "addressLine1": "Sector 52",
              "addressLine2": "Near Metro",
              "city": "",
              "state": "Delhi",
              "pinCode": 110096
            }
            """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void blankState_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {
              "projectName":"ProjectE",
              "addressLine1": "Sector 52",
              "addressLine2": "Near Metro",
              "city": "New Delhi",
              "state": "",
              "pinCode": 110096
            }
            """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void nullPinCode_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {
              "projectName":"ProjectF",
              "addressLine1": "Sector 52",
              "addressLine2": "Near Metro",
              "city": "New Delhi",
              "state": "Delhi",
              "pinCode": null
            }
            """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalidPinCode_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {
              "projectName":"ProjectG",
              "addressLine1": "Sector 52",
              "addressLine2": "Near Metro",
              "city": "New Delhi",
              "state": "Delhi",
              "pinCode": 123
            }
            """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void stringPinCode_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
        {
          "projectName":"ProjectH",
          "addressLine1": "Sector 52",
          "addressLine2": "Near Metro",
          "city": "New Delhi",
          "state": "Delhi",
          "pinCode": "ABC123"
        }
        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalidCityString_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
        {
          "projectName":"ProjectI",
          "addressLine1": "Sector 52",
          "addressLine2": "Near Metro",
          "city": "Delhi123",
          "state": "Delhi",
          "pinCode": 110096
        }
        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalidStateString_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
        {
          "projectName":"ProjectJ",
          "addressLine1": "Sector 52",
          "addressLine2": "Near Metro",
          "city": "New Delhi",
          "state": "@@@@",
          "pinCode": 110096
        }
        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalidAddressLine1_Should_Return_BadRequest() throws Exception
    {
        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
        {
          "projectName":"ProjectK",
          "addressLine1": "@@",
          "city": "New Delhi",
          "state": "Delhi",
          "pinCode": 110096
        }
        """))
                .andExpect(status().isBadRequest());
    }
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
