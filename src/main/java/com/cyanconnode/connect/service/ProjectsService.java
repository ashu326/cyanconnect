package com.cyanconnode.connect.service;

import com.cyanconnode.connect.dto.ProjectResponseDto;
import com.cyanconnode.connect.dto.ProjectsDto;
import com.cyanconnode.connect.entity.Address;
import com.cyanconnode.connect.entity.Projects;
import com.cyanconnode.connect.exception.ConflictException;
import com.cyanconnode.connect.repository.AddressRepository;
import com.cyanconnode.connect.repository.ProjectsRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectsService
{
    private final ProjectsRepository projectsRepository;
    private final AddressRepository addressRepository;

    @Transactional
    public void addProject(@Valid ProjectsDto projectsDto)
    {
        Optional<Projects> existingProject = projectsRepository.findByProjectName(projectsDto.getProjectName());
        if (existingProject.isPresent())
        {
            throw new ConflictException("ProjectName already exists");
        }

        Address address = new Address();
        address.setAddressLine1(projectsDto.getAddressLine1().toLowerCase());
        address.setAddressLine2(projectsDto.getAddressLine2().toLowerCase());
        address.setCity(projectsDto.getCity().toLowerCase());
        address.setState(projectsDto.getState().toLowerCase());
        address.setPinCode(projectsDto.getPinCode());

        Address savedAddress = addressRepository.save(address);

        Projects projectDetails = new Projects();
        projectDetails.setProjectName(projectsDto.getProjectName().toLowerCase());
        projectDetails.setSiteAddress(savedAddress);

        projectsRepository.save(projectDetails);
    }
    public ResponseEntity<Object> getProjects(String projectName, int offset, int limit)
    {
        List<Projects> projects;
        if (projectName != null && !projectName.isBlank())
        {
            projects = projectsRepository.getProjectsQuery(projectName, offset, limit);
        }
        else
        {
            var page = projectsRepository.findAll(
                    org.springframework.data.domain.PageRequest.of(
                            offset / limit,
                            limit,
                            org.springframework.data.domain.Sort.by("projectName")
                    )
            );
            projects = page.getContent();
        }

        List<ProjectResponseDto> responseList = projects.stream().map(project ->
                {
                    var address = project.getSiteAddress();

                    return ProjectResponseDto.builder()
                            .id(project.getId())
                            .projectName(project.getProjectName())
                            .siteAddressId(address != null ? String.valueOf(address.getId()) : null)
                            .addressLine1(address != null ? address.getAddressLine1() : null)
                            .addressLine2(address != null ? address.getAddressLine2() : null)
                            .city(address != null ? address.getCity() : null)
                            .state(address != null ? address.getState() : null)
                            .pinCode(address != null ? address.getPinCode() : 0)
                            .build();
                })
                .toList();

        return ResponseEntity.ok(Map.of(
                "totalProjects", projectsRepository.count(),
                "projects", responseList
        ));
    }
}
