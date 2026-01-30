package com.cyanconnode.connect.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto
{
    private Long id;
    private String name;
    private String email;
    private String phoneNo;
    private String username;
}
