package com.cyanconnode.connect.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest
{
    @Autowired
    private UserService userService;

    @Test
    public void getUsers_Using_Offset_And_Limit()
    {
        ResponseEntity<?> response = userService.getUsers(0, 10);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }


    @Test
    public void getUsers_ShouldReturnUserList_WhenUsersExist()
    {
        ResponseEntity<?> response = userService.getUsers(0, 5);
        assertEquals(200, response.getStatusCodeValue());
        assertInstanceOf(Map.class, response.getBody());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertTrue(body.containsKey("users"));
    }

    @Test
    public void getUsers_ShouldReturnMessage_WhenNoUsersExist()
    {
        ResponseEntity<?> response = userService.getUsers(999, 10);
        assertEquals("No Users found", response.getBody());
    }

    @Test
    public void searchUsersByName_ShouldReturnUsers_WhenNameMatches()
    {
        ResponseEntity<?> response = userService.searchUsersByName("a", 0, 5);
        assertEquals(200, response.getStatusCodeValue());
        assertInstanceOf(Map.class, response.getBody());
    }

    @Test
    public void searchUsersByName_ShouldReturnMessage_WhenNameNotFound()
    {
        ResponseEntity<?> response = userService.searchUsersByName("zzzzzz", 0, 5);
        assertEquals("No Users found with name: zzzzzz", response.getBody());
    }


}
