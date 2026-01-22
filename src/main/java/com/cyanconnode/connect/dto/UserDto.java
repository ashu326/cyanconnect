package com.cyanconnode.connect.dto;

import lombok.Data;

@Data
public class UserDto
{
    private Long userId;
    private String name;
    private String email;
    private  Long phoneNo;
    private String userName;
    private  String password;
}
