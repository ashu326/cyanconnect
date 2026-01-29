package com.cyanconnode.connect.service;

import com.cyanconnode.connect.dto.ProjectsDto;
import com.cyanconnode.connect.entity.Projects;
import com.cyanconnode.connect.repository.ProjectsRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectsService
{
    private final ProjectsRepository projectsRepository;

    public ResponseEntity<String> addProject(@Valid ProjectsDto projectsDto)
    {
        Optional<Projects> existingProject = projectsRepository.findByProjectName(projectsDto.getProjectName());
        if (existingProject.isPresent())
        {
            return ResponseEntity.status(409).body("ProjectName already exists");
        }

        Projects projectDetails = new Projects();
        projectDetails.setProjectName(projectsDto.getProjectName());
        projectDetails.setSiteAddressId(projectsDto.getSiteAddressId());

        projectsRepository.save(projectDetails);
        return ResponseEntity.ok("Project Details Saved Successfully");
    }
}
