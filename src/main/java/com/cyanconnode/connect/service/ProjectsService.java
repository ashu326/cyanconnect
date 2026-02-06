package com.cyanconnode.connect.service;

import com.cyanconnode.connect.dto.ProjectsDto;
import com.cyanconnode.connect.entity.Address;
import com.cyanconnode.connect.entity.Projects;
import com.cyanconnode.connect.exception.ConflictException;
import com.cyanconnode.connect.repository.AddressRepository;
import com.cyanconnode.connect.repository.ProjectsRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectsService
{
    private final ProjectsRepository projectsRepository;
    private final AddressRepository addressRepository;

    public void addProject(@Valid ProjectsDto projectsDto)
    {
        Optional<Projects> existingProject = projectsRepository.findByProjectName(projectsDto.getProjectName());
        if (existingProject.isPresent())
        {
            throw new ConflictException("ProjectName already exists");
        }

        Optional<Address> address = addressRepository.findById(projectsDto.getSiteAddressId());
        if (!address.isPresent())
        {
            throw new ConflictException("Address not Found");
        }

        Projects projectDetails = new Projects();
        projectDetails.setProjectName(projectsDto.getProjectName());
        projectDetails.setSiteAddress(address.get());

        projectsRepository.save(projectDetails);
    }
}
