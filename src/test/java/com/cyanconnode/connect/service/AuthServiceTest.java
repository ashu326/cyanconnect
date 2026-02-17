package com.cyanconnode.connect.service;

import com.cyanconnode.connect.dto.LoginRequestDto;
import com.cyanconnode.connect.dto.LoginResponseDto;
import com.cyanconnode.connect.entity.Users;
import com.cyanconnode.connect.repository.UserRepository;
import com.cyanconnode.connect.utilities.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.anyString;

@SpringBootTest
public class AuthServiceTest
{
    @Autowired
    private AuthService authService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    void loginUser_Should_Return_Token_When_Valid_Credentials() throws Exception
    {
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail("test@gmail.com");
        loginRequest.setPassword("12345");

        Users user = new Users();
        user.setEmail("test@gmail.com");

        Authentication authentication = mock(Authentication.class);

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(user);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        when(authentication.isAuthenticated()).thenReturn(true);

        when(jwtUtil.generateToken(anyMap(), eq("test@gmail.com")))
                .thenReturn("dummy-jwt-token");

        LoginResponseDto response = authService.authUser(loginRequest);
        LoginResponseDto expected = new LoginResponseDto("dummy-jwt-token");

        assertEquals(expected, response);

        verify(jwtUtil).generateToken(anyMap(), eq("test@gmail.com"));

    }

    @Test
    void loginUser_Should_Return_Unauthorized_When_User_Not_Found() throws Exception
    {
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail("test@gmail.com");
        loginRequest.setPassword("12345");

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(null);

        org.junit.jupiter.api.Assertions.assertThrows(
                BadCredentialsException.class,
                () -> authService.authUser(loginRequest)
        );

        verify(authenticationManager, never()).authenticate(any());
    }

    @Test
    void loginUser_Should_Throw_Exception_When_Authentication_Fails()
    {

        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail("test@gmail.com");
        loginRequest.setPassword("wrongPassword");

        Users user = new Users();
        user.setEmail("test@gmail.com");

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(user);

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Invalid"));

        org.junit.jupiter.api.Assertions.assertThrows(
                BadCredentialsException.class,
                () -> authService.authUser(loginRequest)
        );

        verify(jwtUtil, never()).generateToken(anyMap(), anyString());
    }

    @Test
    void loginUser_Should_Throw_Exception_When_Authentication_Not_Authenticated()
    {

        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail("test@gmail.com");
        loginRequest.setPassword("12345");

        Users user = new Users();
        user.setEmail("test@gmail.com");

        Authentication authentication = mock(Authentication.class);

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(user);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        when(authentication.isAuthenticated()).thenReturn(false);

        org.junit.jupiter.api.Assertions.assertThrows(
                BadCredentialsException.class,
                () -> authService.authUser(loginRequest)
        );

        verify(jwtUtil, never()).generateToken(anyMap(), anyString());
    }
}
