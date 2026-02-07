package com.cyanconnode.connect.service;

import com.cyanconnode.connect.dto.ProjectsDto;
import com.cyanconnode.connect.entity.Address;
import com.cyanconnode.connect.entity.Projects;
import com.cyanconnode.connect.exception.ConflictException;
import com.cyanconnode.connect.repository.AddressRepository;
import com.cyanconnode.connect.repository.ProjectsRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public void addProject(@Valid ProjectsDto projectsDto)
    {
        Optional<Projects> existingProject = projectsRepository.findByProjectName(projectsDto.getProjectName());
        if (existingProject.isPresent())
        {
            throw new ConflictException("ProjectName already exists");
        }

        Address address = new Address();
        address.setAddressLine1(projectsDto.getAddressLine1());
        address.setAddressLine2(projectsDto.getAddressLine2());
        address.setCity(projectsDto.getCity());
        address.setState(projectsDto.getState());
        address.setPinCode(projectsDto.getPinCode());

        Address savedAddress = addressRepository.save(address);

        Projects projectDetails = new Projects();
        projectDetails.setProjectName(projectsDto.getProjectName());
        projectDetails.setSiteAddress(savedAddress);

        projectsRepository.save(projectDetails);
    }
}
