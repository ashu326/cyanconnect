package com.cyanconnode.connect.controller;

import com.cyanconnode.connect.dto.LoginRequestDto;
import com.cyanconnode.connect.dto.LoginResponseDto;
import com.cyanconnode.connect.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth-login")
@RequiredArgsConstructor
public class AuthController
{

    private final AuthService loginService;

    @PostMapping
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto)
    {
       return ResponseEntity.ok(loginService.loginUser(loginRequestDto));
    }
}

