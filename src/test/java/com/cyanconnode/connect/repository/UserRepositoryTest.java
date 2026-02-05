package com.cyanconnode.connect.repository;

import com.cyanconnode.connect.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAndFind_test()
    {
        Users user = new Users();
        user.setUserName("saurabh123");
        user.setName("saurabh");
        user.setEmail("saurabh@gmail.com");
        user.setPhoneNo("965454855");
        user.setPassword("12345");

        userRepository.save(user);

        Optional<Users> found =
                Optional.ofNullable(userRepository.findByUserName("saurabh123"));

        assertTrue(found.isPresent());
    }

    @Test
    void findByProjectName_Should_Return_Empty_When_Not_Exists()
    {

        Optional<Users> result =
                Optional.ofNullable(userRepository.findByUserName("UnknownUser"));

        assertTrue(result.isEmpty());
    }
}