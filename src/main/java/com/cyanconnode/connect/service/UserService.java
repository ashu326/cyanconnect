package com.cyanconnode.connect.service;

import com.cyanconnode.connect.dto.UserDto;
import com.cyanconnode.connect.entity.Users;
import com.cyanconnode.connect.repository.UserRepository;
import jakarta.validation.Valid;
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

    public ResponseEntity<String> createUser(@Valid UserDto userDto)
    {

        Optional<Users> existingUser = userRepository.getUserDetails(userDto.getEmail(),
                userDto.getUserName(), userDto.getPhoneNo());

        if (existingUser.isPresent())
        {
            Users existDetails = existingUser.get();

            if (existDetails.getEmail().equals(userDto.getEmail()))
            {
                return ResponseEntity.status(409).body("Email already exists");
            }

            if (existDetails.getUserName().equals(userDto.getUserName()))
            {
                return ResponseEntity.status(409).body("Username already exists");
            }

            if (existDetails.getPhoneNo().equals(userDto.getPhoneNo()))
            {
                return ResponseEntity.status(409).body("Phone number already exists");
            }
        }

        Users userDetails = new Users();
        userDetails.setUserName(userDto.getUserName().toLowerCase());
        userDetails.setEmail(userDto.getEmail().toLowerCase());
        userDetails.setName(userDto.getName().toLowerCase());
        userDetails.setPhoneNo(userDto.getPhoneNo());
        userDetails.setPassword(passwordEncoder.encode(userDto.getPassword().toLowerCase()));
        userRepository.save(userDetails);

        return ResponseEntity.ok("User Details Save Successfully");
    }
}
