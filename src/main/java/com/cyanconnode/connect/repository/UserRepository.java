package com.cyanconnode.connect.repository;

import com.cyanconnode.connect.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>
{
    @Query(value = "SELECT * FROM users ORDER BY id LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Users> findAllWithOffsetLimit(@Param("offset") int offset, @Param("limit") int limit);
}
