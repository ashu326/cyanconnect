package com.cyanconnode.connect.controller;

import com.cyanconnode.connect.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectController
{

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<?> getProjects(@RequestParam int offset, @RequestParam int limit)
    {
        return projectService.getProjects(offset, limit);
    }
}
