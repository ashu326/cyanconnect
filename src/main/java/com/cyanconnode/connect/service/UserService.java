package com.cyanconnode.connect.service;

import com.cyanconnode.connect.dto.UserDto;
import com.cyanconnode.connect.entity.Users;
import com.cyanconnode.connect.exception.ConflictException;
import com.cyanconnode.connect.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Validated
public class UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(@Valid UserDto userDto)
    {

        Optional<Users> existingUser = userRepository.getUserDetails(userDto.getEmail(),
                userDto.getUserName(), userDto.getPhoneNo());

        if (existingUser.isPresent())
        {
            Users existDetails = existingUser.get();

            if (existDetails.getEmail().equals(userDto.getEmail()))
            {
                throw new ConflictException("Email already exists");
            }

            if (existDetails.getUserName().equals(userDto.getUserName()))
            {
                throw new ConflictException("Username already exists");
            }

            if (existDetails.getPhoneNo().equals(userDto.getPhoneNo()))
            {
                throw new ConflictException("Phone number already exists");
            }
        }

        Users userDetails = new Users();
        userDetails.setUserName(userDto.getUserName());
        userDetails.setEmail(userDto.getEmail());
        userDetails.setName(userDto.getName());
        userDetails.setPhoneNo(userDto.getPhoneNo());
        userDetails.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(userDetails);
    }
}
