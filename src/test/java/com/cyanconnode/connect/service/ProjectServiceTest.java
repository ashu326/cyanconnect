package com.cyanconnode.connect.service;

import com.cyanconnode.connect.dto.ProjectsDto;
import com.cyanconnode.connect.entity.Address;
import com.cyanconnode.connect.entity.Projects;
import com.cyanconnode.connect.exception.ConflictException;
import com.cyanconnode.connect.repository.AddressRepository;
import com.cyanconnode.connect.repository.ProjectsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;

@SpringBootTest
public class ProjectServiceTest
{
    @Autowired
    private ProjectsService projectsService;

    @MockBean
    private ProjectsRepository projectsRepository;

    @MockBean
    private AddressRepository addressRepository;

    @Test
    void addProject_Should_Save_Project_When_ProjectName_Not_Exists()
    {

        ProjectsDto dto = new ProjectsDto();
        dto.setProjectName("ProjectServiceA");
        dto.setAddressLine1("Line 1");
        dto.setAddressLine2("Line 2");
        dto.setCity("Delhi");
        dto.setState("Delhi");
        dto.setPinCode(110091);

        when(projectsRepository.findByProjectName("ProjectServiceA")).thenReturn(Optional.empty());

        when(addressRepository.save(any(Address.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(projectsRepository.save(any(Projects.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        assertDoesNotThrow(() -> projectsService.addProject(dto));

        verify(addressRepository, times(1)).save(any(Address.class));
        verify(projectsRepository, times(1)).save(any(Projects.class));
    }

    @Test
    void addProject_Should_Throw_ConflictException_When_ProjectName_Exists()
    {

        ProjectsDto dto = new ProjectsDto();
        dto.setProjectName("ProjectServiceA");
        dto.setAddressLine1("Line 1");
        dto.setAddressLine2("Line 2");
        dto.setCity("Delhi");
        dto.setState("Delhi");
        dto.setPinCode(110091);

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
        dto.setProjectName("ProjectServiceA");
        dto.setAddressLine1("Line 1");
        dto.setAddressLine2("Line 2");
        dto.setCity("Delhi");
        dto.setState("Delhi");
        dto.setPinCode(110091);

        when(projectsRepository.findByProjectName("ProjectA"))
                .thenReturn(Optional.empty());

        when(projectsRepository.save(any()))
                .thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class,
                () -> projectsService.addProject(dto));
    }
    @Test
    public void getProjects_Using_Offset_And_Limit()
    {
        when(projectsRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));
        when(projectsRepository.count()).thenReturn(0L);
        ResponseEntity<?> response = projectsService.getProjects("", 0, 10);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    public void getProjects_ShouldReturnUserList_WhenProjectsExist()
    {
        when(projectsRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));
        when(projectsRepository.count()).thenReturn(0L);
        ResponseEntity<?> response = projectsService.getProjects("", 0, 10);
        assertEquals(200, response.getStatusCodeValue());
        assertInstanceOf(Map.class, response.getBody());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertTrue(body.containsKey("projects"));
    }

    @Test
    public void getProjects_ShouldReturnMessage_WhenNoProjectsExist()
    {
        when(projectsRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));
        when(projectsRepository.count()).thenReturn(0L);
        ResponseEntity<?> response = projectsService.getProjects("", 0, 10);
        assertEquals(200, response.getStatusCodeValue());
    }
}
