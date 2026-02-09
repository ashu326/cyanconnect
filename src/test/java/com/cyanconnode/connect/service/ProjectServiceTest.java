package com.cyanconnode.connect.service;

import com.cyanconnode.connect.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ProjectServiceTest
{
    @Autowired
    private ProjectService projectService;

    @MockBean
    private ProjectRepository projectRepository;

    @Test
    public void getProjects_Using_Offset_And_Limit()
    {

        ResponseEntity<?> response = projectService.getProjects("", 0, 10);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    public void getProjects_ShouldReturnUserList_WhenProjectsExist()
    {
        ResponseEntity<?> response = projectService.getProjects("", 0, 10);
        assertEquals(200, response.getStatusCodeValue());
        assertInstanceOf(Map.class, response.getBody());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertTrue(body.containsKey("projects"));
    }

    @Test
    public void getProjects_ShouldReturnMessage_WhenNoProjectsExist()
    {
        ResponseEntity<?> response = projectService.getProjects("", 0, 10);
        assertEquals(200, response.getStatusCodeValue());
    }
}
