package com.cyanconnode.connect.repository;

import com.cyanconnode.connect.entity.Projects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ProjectsRepositoryTest
{
    @Autowired
    private ProjectsRepository projectsRepository;

    @Test
    void saveAndFind_test()
    {
        Projects projectsDetails = new Projects();
        projectsDetails.setProjectName("ProjectRepoTest");
        projectsDetails.setSiteAddressId("Address1");

        projectsRepository.save(projectsDetails);

        Optional<Projects> found = projectsRepository
                .findByProjectName(projectsDetails.getProjectName());
        assertTrue(found.isPresent());
    }
}
