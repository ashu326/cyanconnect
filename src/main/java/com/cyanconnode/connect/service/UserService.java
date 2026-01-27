package com.cyanconnode.connect.service;

import com.cyanconnode.connect.entity.Users;
import com.cyanconnode.connect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> createUser(Users user)
    {
        if (user.getEmail() == null || user.getEmail().isBlank())
        {
            return ResponseEntity.badRequest().body("Email cannot be null");
        }

        if (user.getUserName() == null || user.getUserName().isBlank())
        {
            return ResponseEntity.badRequest().body("Username cannot be null");
        }

        if (user.getPhoneNo() == null)
        {
            return ResponseEntity.badRequest().body("Phone number cannot be null");
        }

        if (user.getPassword() == null || user.getPassword().isBlank())
        {
            return ResponseEntity.badRequest().body("Password cannot be blank");
        }

        Optional<Users> existingUser = userRepository.findExistingUser(user.getEmail(),
                user.getUserName(), user.getPhoneNo());

        if (existingUser.isPresent())
        {
            Users existDetails = existingUser.get();

            if (existDetails.getEmail().equals(user.getEmail()))
            {
                return ResponseEntity.status(409).body("Email already exists");
            }

            if (existDetails.getUserName().equals(user.getUserName()))
            {
                return ResponseEntity.status(409).body("Username already exists");
            }

            if (existDetails.getPhoneNo().equals(user.getPhoneNo()))
            {
                return ResponseEntity.status(409).body("Phone number already exists");
            }
        }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);

            return ResponseEntity.ok("User Details Save Successfully");
    }

}
