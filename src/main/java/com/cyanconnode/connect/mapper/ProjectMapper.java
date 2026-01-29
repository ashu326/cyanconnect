package com.cyanconnode.connect.mapper;

import com.cyanconnode.connect.dto.ProjectDto;
import com.cyanconnode.connect.entity.Projects;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    //ENTITY to DTO
    public ProjectDto toDto(Projects project)
    {
        if (project == null) return null;

        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(project.getId());
        projectDto.setProjectName(project.getProjectName());
        projectDto.setSiteAddressId(project.getSiteAddressId());
        return projectDto;
    }
}
