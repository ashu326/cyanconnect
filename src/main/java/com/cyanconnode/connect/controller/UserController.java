package com.cyanconnode.connect.controller;


import com.cyanconnode.connect.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController
{
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getUsers(@RequestParam int offset, @RequestParam int limit)
    {
        return userService.getUsers(offset, limit);
    }
}
