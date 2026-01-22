package com.cyanconnode.connect.repository;

import com.cyanconnode.connect.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<Users, Long>
{

    Users findByUserName(String userName);

    Users findByEmail(String email);

    Users findByPhoneNo(Long phoneNo);
}
