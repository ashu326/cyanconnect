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
}
