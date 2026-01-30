package com.cyanconnode.connect.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectsDto
{
    @NotBlank(message = "ProjectName cannot be blank")
    private String projectName;

    @NotBlank(message = "SiteAddressId cannot be blank")
    private String siteAddressId;
}
