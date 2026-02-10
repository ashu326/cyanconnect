package com.cyanconnode.connect.service;

import com.cyanconnode.connect.dto.ProjectResponseDto;
import com.cyanconnode.connect.repository.ProjectsRepository;
import com.cyanconnode.connect.entity.Projects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Validated
public class ProjectsService
{
    private final ProjectsRepository projectsRepository;
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

        List<ProjectResponseDto> responseList = projects.stream()
                .map(project -> ProjectResponseDto.builder()
                        .id(project.getProjectId())
                        .projectName(project.getProjectName())
                        .siteAddressId(project.getSiteAddressId())
                        .build())
                .toList();

        return ResponseEntity.ok(Map.of(
                "totalProjects", projectsRepository.count(),
                "projects", responseList
        ));
    }
}
