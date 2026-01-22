package com.cyanconnode.connect.service;

import com.cyanconnode.connect.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService 
{

    ResponseEntity<?> createUser(UserDto userDto);
}
