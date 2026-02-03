package com.cyanconnode.connect.service;

import com.cyanconnode.connect.dto.ProjectsDto;
import com.cyanconnode.connect.entity.Projects;
import com.cyanconnode.connect.exception.ConflictException;
import com.cyanconnode.connect.repository.ProjectsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProjectServiceTest
{
    @Autowired
    private ProjectsService projectsService;

    @MockBean
    private ProjectsRepository projectsRepository;

    @Test
    void addProject_Should_Save_Project_When_ProjectName_Not_Exists()
    {

        ProjectsDto dto = new ProjectsDto();
        dto.setProjectName("ProjectServiceA");
        dto.setSiteAddressId("ADDRService1");

        when(projectsRepository.findByProjectName("ProjectServiceA"))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() -> projectsService.addProject(dto));

        verify(projectsRepository, times(1)).save(any(Projects.class));
    }

    @Test
    void addProject_Should_Throw_ConflictException_When_ProjectName_Exists()
    {

        ProjectsDto dto = new ProjectsDto();
        dto.setProjectName("ProjectServiceA");
        dto.setSiteAddressId("ADDRService1");

        Projects existingProject = new Projects();
        existingProject.setProjectName("ProjectServiceA");

        when(projectsRepository.findByProjectName("ProjectServiceA"))
                .thenReturn(Optional.of(existingProject));

        ConflictException exception = assertThrows(
                ConflictException.class,
                () -> projectsService.addProject(dto)
        );

        assertEquals("ProjectName already exists", exception.getMessage());

        verify(projectsRepository, never()).save(any());
    }

    @Test
    void addProject_Should_Throw_RuntimeException_When_Save_Fails()
    {

        ProjectsDto dto = new ProjectsDto();
        dto.setProjectName("ProjectA");
        dto.setSiteAddressId("ADDR1");

        when(projectsRepository.findByProjectName("ProjectA"))
                .thenReturn(Optional.empty());

        when(projectsRepository.save(any()))
                .thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class,
                () -> projectsService.addProject(dto));
    }
}
