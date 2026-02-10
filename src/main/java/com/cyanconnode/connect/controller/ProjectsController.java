package com.cyanconnode.connect.controller;

import com.cyanconnode.connect.service.ProjectsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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
}
