package com.cyanconnode.connect.service;

import com.cyanconnode.connect.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class UserServiceTest
{

    @Autowired
    private UserService userService;

    @Test
    void validDto_Should_Create_User_Successfully()
    {
        UserDto userDetails = new UserDto();
        userDetails.setUserName("saurabh123");
        userDetails.setName("saurabh");
        userDetails.setEmail("saurabh@gmail.com");
        userDetails.setPhoneNo("965454855");
        userDetails.setPassword("12345");

        ResponseEntity<?> saved = userService.createUser(userDetails);

        assertNotNull(saved);
    }

    @Test
    void duplicateEmail_Should_Return_Conflict()
    {
        UserDto firstUser = new UserDto();
        firstUser.setName("user1");
        firstUser.setUserName("user1");
        firstUser.setEmail("dup@gmail.com");
        firstUser.setPhoneNo("9999999991");
        firstUser.setPassword("12345");

        userService.createUser(firstUser);

        UserDto duplicateUser = new UserDto();
        duplicateUser.setName("user2");
        duplicateUser.setUserName("user2");
        duplicateUser.setEmail("dup@gmail.com");
        duplicateUser.setPhoneNo("9999999992");
        duplicateUser.setPassword("12345");

        ResponseEntity<String> response =
                userService.createUser(duplicateUser);

        assertEquals(409, response.getStatusCode().value());
    }
}
