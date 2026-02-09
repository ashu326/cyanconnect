package com.cyanconnode.connect.controller;

import com.cyanconnode.connect.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/projects")
@RequiredArgsConstructor
public class ProjectController
{
    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<Object> getProjects(@RequestParam(defaultValue = "0") int offset,
                                           @RequestParam(defaultValue = "10") int limit,
                                           @RequestParam(required = false) String projectName)
    {
        return projectService.getProjects(projectName, offset, limit);
    }
}
