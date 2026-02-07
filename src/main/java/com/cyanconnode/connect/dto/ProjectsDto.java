package com.cyanconnode.connect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectsDto
{
    @NotBlank(message = "ProjectName cannot be blank")
    private String projectName;

    @NotBlank(message = "Address Line 1 cannot be blank")
    private String addressLine1;

    private String addressLine2;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @NotBlank(message = "State cannot be blank")
    private String state;

    @NotNull(message = "PinCode cannot be blank")
    private int pinCode;
}
