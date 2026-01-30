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
    //Get All Users
    @Query(value = """
    SELECT * FROM users
    WHERE (:name IS NULL OR name LIKE CONCAT('%', :name, '%'))
    ORDER BY id
    LIMIT :limit OFFSET :offset
    """, nativeQuery = true)
    List<Users> getUsersQuery(@Param("name") String name, @Param("offset") int offset, @Param("limit") int limit);

}
