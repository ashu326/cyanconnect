package com.cyanconnode.connect.repository;

import com.cyanconnode.connect.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Projects, Long>
{

    @Query(value = "SELECT * FROM projects " +
            "ORDER BY id " +
            " LIMIT :limit OFFSET :offset",
             nativeQuery = true)
    List<Projects> findAllWithOffsetLimit(@Param("offset") int offset, @Param("limit") int limit);
}
