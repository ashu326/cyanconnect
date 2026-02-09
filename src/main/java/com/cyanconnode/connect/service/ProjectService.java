package com.cyanconnode.connect.service;

import com.cyanconnode.connect.dto.ProjectResponseDto;
import com.cyanconnode.connect.repository.ProjectRepository;
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
public class ProjectService
{
    private final ProjectRepository projectRepository;
    public ResponseEntity<Object> getProjects(String name, int offset, int limit)
    {
        List<Projects> projects = projectRepository.getProjectsQuery(name, offset, limit);

        List<ProjectResponseDto> responseList = projects.stream()
                .map(project -> ProjectResponseDto.builder()
                        .id(project.getProjectId())
                        .projectName(project.getProjectName())
                        .siteAddressId(project.getSiteAddressId())
                        .build())
                .toList();

        return ResponseEntity.ok(Map.of(
                "totalProjects", projectRepository.count(),
                "projects", responseList
        ));
    }
}
