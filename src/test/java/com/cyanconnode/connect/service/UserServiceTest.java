package com.cyanconnode.connect.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest
{
    @Autowired
    private UserService userService;

    @Test
    public void getUsers_Using_Offset_And_Limit()
    {

        ResponseEntity<?> user = userService.getUsers(0, 10);
        assertNotNull(user);
    }
}
