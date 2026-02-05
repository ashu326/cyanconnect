package com.cyanconnode.connect.service;

import com.cyanconnode.connect.dto.ProjectsDto;
import com.cyanconnode.connect.entity.Projects;
import com.cyanconnode.connect.exception.ConflictException;
import com.cyanconnode.connect.repository.ProjectsRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectsService
{
    private final ProjectsRepository projectsRepository;

    public void addProject(@Valid ProjectsDto projectsDto)
    {
        Optional<Projects> existingProject = projectsRepository.findByProjectName(projectsDto.getProjectName());
        if (existingProject.isPresent())
        {
            throw new ConflictException("ProjectName already exists");
        }

        Projects projectDetails = new Projects();
        projectDetails.setProjectName(projectsDto.getProjectName());
        projectDetails.setSiteAddressId(projectsDto.getSiteAddressId());

        projectsRepository.save(projectDetails);
    }
}
