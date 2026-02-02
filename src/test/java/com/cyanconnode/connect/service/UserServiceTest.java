package com.cyanconnode.connect.service;

import com.cyanconnode.connect.dto.UserDto;
import com.cyanconnode.connect.entity.Users;
import com.cyanconnode.connect.exception.ConflictException;
import com.cyanconnode.connect.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;


@SpringBootTest
class UserServiceTest
{

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void validDto_Should_Create_User_Successfully()
    {
        UserDto userDetails = new UserDto();
        userDetails.setUserName("saurabh123");
        userDetails.setName("saurabh");
        userDetails.setEmail("saurabh@gmail.com");
        userDetails.setPhoneNo("9654548555");
        userDetails.setPassword("12345");

        when(userRepository.getUserDetails(anyString(), anyString(), anyString()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(anyString()))
                .thenReturn("hashedPassword");

        assertDoesNotThrow(() -> userService.createUser(userDetails));

        verify(userRepository, times(1)).save(any(Users.class));
    }

    @Test
    void invalidEmail_Should_Throw_Validation_Exception()
    {
        UserDto userDetails = new UserDto();
        userDetails.setUserName("userServiceTest");
        userDetails.setName("userServiceTest");
        userDetails.setEmail("invalid-email");
        userDetails.setPhoneNo("985454955");
        userDetails.setPassword("12345");

        assertThrows(ConstraintViolationException.class, () -> userService.createUser(userDetails));
    }

    @Test
    void missingEmail_Should_Return_BadRequest()
    {
        UserDto userDetails = new UserDto();
        userDetails.setUserName("userServiceTest");
        userDetails.setName("userServiceTest");
        userDetails.setPhoneNo("985454955");
        userDetails.setPassword("12345");

        assertThrows(ConstraintViolationException.class, () -> userService.createUser(userDetails));
    }

    @Test
    void blankName_Should_Return_BadRequest()
    {
        UserDto userDetails = new UserDto();
        userDetails.setUserName("");
        userDetails.setName("userServiceTest");
        userDetails.setEmail("user@gmail.com");
        userDetails.setPhoneNo("985454955");
        userDetails.setPassword("12345");

        assertThrows(ConstraintViolationException.class, () -> userService.createUser(userDetails));
    }

    @Test
    void invalidPhoneNumber_Should_Return_BadRequest()
    {
        UserDto userDetails = new UserDto();
        userDetails.setUserName("userService");
        userDetails.setName("userServiceTest");
        userDetails.setEmail("user@gmail.com");
        userDetails.setPhoneNo("98545");
        userDetails.setPassword("12345");

        assertThrows(ConstraintViolationException.class, () -> userService.createUser(userDetails));
    }

    @Test
    void blankPassword_Should_Return_BadRequest()
    {
        UserDto userDetails = new UserDto();
        userDetails.setUserName("userService");
        userDetails.setName("userServiceTest");
        userDetails.setEmail("user@gmail.com");
        userDetails.setPhoneNo("98545");
        userDetails.setPassword("");

        assertThrows(ConstraintViolationException.class, () -> userService.createUser(userDetails));
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

        when(userRepository.getUserDetails(
                anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(new Users()));

        ConflictException exception = assertThrows(
                ConflictException.class,
                () -> userService.createUser(duplicateUser)
        );

        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void repositorySaveFails_Should_Throw_Exception()
    {
        UserDto userDetails = new UserDto();
        userDetails.setUserName("repoFailUser");
        userDetails.setName("repoFailUser");
        userDetails.setEmail("repofail@gmail.com");
        userDetails.setPhoneNo("9999999999");
        userDetails.setPassword("12345");

        when(userRepository.getUserDetails(any(), any(), any()))
                .thenReturn(Optional.empty());

        when(userRepository.save(any()))
                .thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.createUser(userDetails));

        assertEquals("Database error", exception.getMessage());
    }

    @Test
    public void getUsers_Using_Offset_And_Limit()
    {

        ResponseEntity<?> response = userService.getUsers("", 0, 10);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    public void getUsers_ShouldReturnUserList_WhenUsersExist()
    {
        ResponseEntity<?> response = userService.getUsers("", 0, 10);
        assertEquals(200, response.getStatusCodeValue());
        assertInstanceOf(Map.class, response.getBody());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertTrue(body.containsKey("users"));
    }

    @Test
    public void getUsers_ShouldReturnMessage_WhenNoUsersExist()
    {
        ResponseEntity<?> response = userService.getUsers("", 0, 10);
        assertEquals(200, response.getStatusCodeValue());
    }
}
