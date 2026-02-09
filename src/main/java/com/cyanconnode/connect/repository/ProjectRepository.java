package com.cyanconnode.connect.repository;

import com.cyanconnode.connect.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectRepository extends JpaRepository<Projects, Long>
{
    //Get All Projects
    @Query(value = """
SELECT * FROM projects
WHERE (:projectName IS NULL OR project_name LIKE CONCAT('%', :projectName, '%'))
ORDER BY project_name
LIMIT :limit OFFSET :offset
""", nativeQuery = true)
    List<Projects> getProjectsQuery(
            @Param("projectName") String projectName,
            @Param("offset") int offset,
            @Param("limit") int limit
    );


}