package com.cyanconnode.connect.service;

import com.cyanconnode.connect.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class UserServiceTest
{

    @Autowired
    private UserService userService;

    @Test
    void createUser_test()
    {
        Users user = new Users();
        user.setUserName("saurabh123");
        user.setName("saurabh");
        user.setEmail("saurabh@gmail.com");
        user.setPhoneNo(965454855L);
        user.setPassword("12345");

        ResponseEntity<?> saved = userService.createUser(user);

        assertNotNull(saved);
    }


    @Test
    void createUser_nullDto()
    {
        assertThrows(RuntimeException.class,
                () -> userService.createUser(null));
    }


}
