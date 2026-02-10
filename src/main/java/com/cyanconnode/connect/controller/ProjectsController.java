package com.cyanconnode.connect.controller;

import com.cyanconnode.connect.dto.ProjectsDto;
import com.cyanconnode.connect.service.ProjectsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/projects")
@RequiredArgsConstructor
public class ProjectsController
{
    private final ProjectsService projectsService;

    @GetMapping
    public ResponseEntity<Object> getProjects(@RequestParam(defaultValue = "0") int offset,
                                           @RequestParam(defaultValue = "10") int limit,
                                           @RequestParam(required = false) String projectName)
    {
        return projectsService.getProjects(projectName, offset, limit);
    }

    @PostMapping
    public ResponseEntity<Void> addProject(@Valid @RequestBody ProjectsDto projectsDto)
    {
        projectsService.addProject(projectsDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
