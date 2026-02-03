package com.cyanconnode.connect.repository;

import com.cyanconnode.connect.entity.Projects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ProjectRepositoryTest
{
    @Autowired
    private ProjectsRepository projectsRepository;

    @Test
    void saveAndFind_test()
    {
        Projects projects = new Projects();
        projects.setProjectName("ProjectRepoTest");
        projects.setSiteAddressId("AddressRepoTest");

        projectsRepository.save(projects);

        Optional<Optional<Projects>> found =
                Optional.ofNullable(projectsRepository.findByProjectName("ProjectRepoTest"));

        assertTrue(found.isPresent());
    }

    @Test
    void findByProjectName_Should_Return_Empty_When_Not_Exists()
    {

        Optional<Projects> result =
                projectsRepository.findByProjectName("UnknownProject");

        assertTrue(result.isEmpty());
    }
}
