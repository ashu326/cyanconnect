package com.cyanconnode.connect.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProjectsDto
{
    @NotBlank(message = "ProjectName cannot be blank")
    private String projectName;

    @NotBlank(message = "Address Line 1 cannot be blank")
    @Size(min = 3, message = "Invalid address")
    private String addressLine1;

    private String addressLine2;

    @NotBlank(message = "City cannot be blank")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Invalid city")
    private String city;

    @NotBlank(message = "State cannot be blank")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Invalid state")
    private String state;

    @NotNull(message = "PinCode cannot be blank")
    @Min(value = 100000, message = "Invalid pinCode")
    @Max(value = 999999, message = "Invalid pinCode")
    private int pinCode;
}
