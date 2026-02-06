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

        Address address = new Address();
        address.setId(2L);
        address.setAddressLine1("Test Address Line 2");
        address.setCity("Delhi");
        address.setState("Delhi");
        address.setPinCode(110091);

        ProjectsDto dto = new ProjectsDto();
        dto.setProjectName("ProjectServiceA");
        dto.setSiteAddressId(2L);

        when(projectsRepository.findByProjectName("ProjectServiceA"))
                .thenReturn(Optional.empty());

        when(addressRepository.findById(2L)).thenReturn(Optional.of(address));

        assertDoesNotThrow(() -> projectsService.addProject(dto));

        verify(projectsRepository, times(1)).save(any(Projects.class));
    }

    @Test
    void addProject_Should_Throw_ConflictException_When_ProjectName_Exists()
    {

        Address address = new Address();
        address.setId(2L);
        address.setAddressLine1("Test Address Line 2");
        address.setCity("Delhi");
        address.setState("Delhi");
        address.setPinCode(110091);

        ProjectsDto dto = new ProjectsDto();
        dto.setProjectName("ProjectServiceA");
        dto.setSiteAddressId(2L);

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
        Address address = new Address();
        address.setId(2L);
        address.setAddressLine1("Test Address Line 2");
        address.setCity("Delhi");
        address.setState("Delhi");
        address.setPinCode(110091);

        ProjectsDto dto = new ProjectsDto();
        dto.setProjectName("ProjectA");
        dto.setSiteAddressId(2L);

        when(projectsRepository.findByProjectName("ProjectA"))
                .thenReturn(Optional.empty());

        when(projectsRepository.save(any()))
                .thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class,
                () -> projectsService.addProject(dto));
    }
}
