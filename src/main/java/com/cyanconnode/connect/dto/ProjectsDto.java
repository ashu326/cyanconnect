package com.cyanconnode.connect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectsDto
{
    @NotBlank(message = "ProjectName cannot be blank")
    private String projectName;

    @NotNull(message = "SiteAddressId cannot be blank")
    private Long siteAddressId;
}
