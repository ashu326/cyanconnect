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
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
