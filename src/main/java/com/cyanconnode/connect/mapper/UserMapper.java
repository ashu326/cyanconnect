package com.cyanconnode.connect.mapper;

import com.cyanconnode.connect.dto.UserDto;
import com.cyanconnode.connect.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class UserMapper
{
    //ENTITY to DTO
    public UserDto toDto(Users user)
    {
        if (user == null) return null;
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNo(user.getPhoneNo());
        userDto.setUsername(user.getUserName());
        return userDto;
    }
}
