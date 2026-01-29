package com.cyanconnode.connect.repository;

import com.cyanconnode.connect.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAndFind_test()
    {
        Users user = new Users();
        user.setUserName("saurabh12");
        user.setName("saurabh12");
        user.setEmail("saurabh1@gmail.com");
        user.setPhoneNo("965457855");
        user.setPassword("12345");

        userRepository.save(user);

        Optional<Users> found =
                Optional.ofNullable(userRepository.findByUserName("saurabh123"));

        assertTrue(found.isPresent());
    }
}
