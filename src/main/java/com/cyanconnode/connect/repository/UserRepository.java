package com.cyanconnode.connect.repository;

import com.cyanconnode.connect.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Users, Long>
{

    Users findByUserName(String userName);

    @Query(""" 
            SELECT u FROM Users u WHERE u.email = :email 
            OR u.userName = :userName OR u.phoneNo = :phoneNo 
            """)
    Optional<Users> getUserDetails(
            @Param("email") String email,
            @Param("userName") String userName,
            @Param("phoneNo") String phoneNo
    );
    //Get All Users
    @Query(value = """
    SELECT * FROM users
    WHERE (:name IS NULL OR name LIKE CONCAT('%', :name, '%'))
    ORDER BY name
    LIMIT :limit OFFSET :offset
    """, nativeQuery = true)
    List<Users> getUsersQuery(@Param("name") String name, @Param("offset") int offset, @Param("limit") int limit);

    Users findByEmail(String email);
}
