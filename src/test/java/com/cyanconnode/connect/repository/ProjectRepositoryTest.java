package com.cyanconnode.connect.repository;

import com.cyanconnode.connect.entity.Address;
import com.cyanconnode.connect.entity.Projects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ProjectRepositoryTest
{
    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    void saveAndFind_test()
    {
        Address address = new Address();
        address.setAddressLine1("Test Address Line 3");
        address.setCity("Haryana");
        address.setState("Haryana");
        address.setPinCode(110092);

        Address savedAddress = addressRepository.save(address);

        Projects projects = new Projects();
        projects.setProjectName("ProjectRepoTest");
        projects.setSiteAddress(savedAddress);

        projectsRepository.save(projects);

        Optional<Projects> found = projectsRepository.findByProjectName("ProjectRepoTest");

        assertTrue(found.isPresent());
        assertEquals("ProjectRepoTest", found.get().getProjectName());
        assertEquals(savedAddress.getId(), found.get().getSiteAddress().getId());
    }

    @Test
    void findByProjectName_Should_Return_Empty_When_Not_Exists()
    {

        Optional<Projects> result =
                projectsRepository.findByProjectName("UnknownProject");

        assertTrue(result.isEmpty());
    }
}
