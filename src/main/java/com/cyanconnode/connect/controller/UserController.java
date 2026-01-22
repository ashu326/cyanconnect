package com.cyanconnode.connect.controller;

import com.cyanconnode.connect.dto.UserDto;
import com.cyanconnode.connect.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(HttpServletRequest request, @RequestBody UserDto userDto)
    {
        if (userDto == null)
        {
            return ResponseEntity.status(401).body("Request body cannot be null");
        }

        return userService.createUser(userDto);
    }
}
