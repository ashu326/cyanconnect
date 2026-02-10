package com.cyanconnode.connect.repository;

import com.cyanconnode.connect.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectsRepository extends JpaRepository<Projects, Long>
{
    Optional<Projects> findByProjectName(String projectName);
}
