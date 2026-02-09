package com.cyanconnode.connect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.Data;

@Data
public class ProjectsDto
{
    @NotBlank(message = "ProjectName cannot be blank")
    private String projectName;

    @NotBlank(message = "Address Line 1 cannot be blank")
    @Size(min = 3, message = "Invalid address")
    @Pattern(regexp = "^[A-Za-z0-9][A-Za-z0-9\\s,.-/#]{2,99}$", message = "Invalid address")
    private String addressLine1;

    @Pattern(regexp = "^[A-Za-z0-9][A-Za-z0-9\\s,.-/#]{2,99}$", message = "Invalid address")
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
