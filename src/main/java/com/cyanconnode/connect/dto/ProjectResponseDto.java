package com.cyanconnode.connect.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectResponseDto
{
    private Long id;
    private String projectName;
    private String siteAddressId;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private int pinCode;
}
