package com.cyanconnode.connect.controller;

import com.cyanconnode.connect.dto.ProjectsDto;
import com.cyanconnode.connect.service.ProjectsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1/projects")
public class ProjectsController
{
    private final ProjectsService projectsService;

    @PostMapping
    public ResponseEntity<Void> addProject(@Valid @RequestBody ProjectsDto projectsDto)
    {
        projectsService.addProject(projectsDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
