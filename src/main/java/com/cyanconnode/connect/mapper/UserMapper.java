package com.cyanconnode.connect.mapper;


import com.cyanconnode.connect.dto.UserDto;
import com.cyanconnode.connect.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class UserMapper
{

    //DTO → Entity
    public Users toEntity(UserDto dto)
    {
        if (dto == null) return null;

        Users user = new Users();
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setPhoneNo(dto.getPhoneNo());
        return user;
    }
}
