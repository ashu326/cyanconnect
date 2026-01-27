package com.cyanconnode.connect.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest
{
    @Autowired
    private UserService userService;

    @Test
    public void getUsers()
    {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(0, 5);

        ResponseEntity<?> user = userService.getUsers(pageable);
        assertNotNull(user);
    }
}
