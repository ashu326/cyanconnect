package com.cyanconnode.connect.service;

import com.cyanconnode.connect.dto.ProjectDto;
import com.cyanconnode.connect.entity.Projects;
import com.cyanconnode.connect.mapper.ProjectMapper;
import com.cyanconnode.connect.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProjectService
{
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ResponseEntity<?> getProjects(int offset, int limit)
    {

        List<Projects> projects = projectRepository.findAllWithOffsetLimit(offset, limit);

        if (projects.isEmpty())
        {
            return ResponseEntity.ok("No Projects found");
        }

        List<ProjectDto> userDtoList = projects.stream()
                .map(projectMapper::toDto)
                .toList();

        return ResponseEntity.ok(Map.of(
                "offset", offset,
                "limit", limit,
                "projectsCount", projectRepository.count(),
                "projects", userDtoList
        ));
    }
}
