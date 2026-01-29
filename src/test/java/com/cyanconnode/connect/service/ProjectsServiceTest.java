package com.cyanconnode.connect.service;

import com.cyanconnode.connect.dto.ProjectsDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProjectsServiceTest
{
    @Autowired
    private ProjectsService projectsService;

    @Test
    void addProject_WhenProjectNotExists_ShouldSave()
    {

        ProjectsDto dto = new ProjectsDto();
        dto.setProjectName("TestProject");
        dto.setSiteAddressId("Address1");

        ResponseEntity<?> saved = projectsService.addProject(dto);

        assertNotNull(saved);
    }

    @Test
    void duplicateEmail_Should_Return_Conflict()
    {
        ProjectsDto firstProject = new ProjectsDto();
        firstProject.setProjectName("TestProject");
        firstProject.setSiteAddressId("Address1");

        projectsService.addProject(firstProject);

        ProjectsDto duplicateProject = new ProjectsDto();
        duplicateProject.setProjectName("TestProject");
        duplicateProject.setSiteAddressId("Address1");

        ResponseEntity<String> response =
                projectsService.addProject(duplicateProject);

        assertEquals(409, response.getStatusCode().value());
    }
}
