package com.cyanconnode.connect.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProjectServiceTest
{
    @Autowired
    private ProjectService projectService;

    @Test
    public void getUsers_Using_Offset_And_Limit()
    {
        ResponseEntity<?> response = projectService.getProjects(0, 10);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    public void getUsers_ShouldReturnUserList_WhenUsersExist()
    {
        ResponseEntity<?> response = projectService.getProjects(0, 10);
        assertEquals(200, response.getStatusCodeValue());
        assertInstanceOf(Map.class, response.getBody());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertTrue(body.containsKey("projects"));
    }

    @Test
    public void getUsers_ShouldReturnMessage_WhenNoUsersExist()
    {
        ResponseEntity<?> response = projectService.getProjects(999, 10);
        assertEquals("No Projects found", response.getBody());
    }

}
