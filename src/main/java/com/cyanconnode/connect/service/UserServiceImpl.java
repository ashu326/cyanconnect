package com.cyanconnode.connect.service;

import com.cyanconnode.connect.dto.UserDto;
import com.cyanconnode.connect.entity.Users;
import com.cyanconnode.connect.mapper.UserMapper;
import com.cyanconnode.connect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> createUser(UserDto userDto)
    {

        Users existingUserByUsername = userRepository.findByUserName(userDto.getUserName());
        if (existingUserByUsername != null)
        {
            return ResponseEntity.status(409).body("Username already exists");
        }

        Users existingUserByEmail = userRepository.findByEmail(userDto.getEmail());
        if (existingUserByEmail != null)
        {
            return ResponseEntity.status(409).body("Email already exists");
        }

        Users existingUserByPhoneNo = userRepository.findByPhoneNo(userDto.getPhoneNo());
        if (existingUserByPhoneNo != null)
        {
            return ResponseEntity.status(409).body("Phone No. already exists");
        }

        Users saveUser = userMapper.toEntity(userDto);
        if (userDto.getPassword() != null && !userDto.getPassword().isBlank())
        {
            saveUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        userRepository.save(saveUser);
        return ResponseEntity.ok("User Details Save Successfully");
    }
}
