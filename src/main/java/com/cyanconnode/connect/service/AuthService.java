package com.cyanconnode.connect.service;

import com.cyanconnode.connect.dto.LoginRequestDto;
import com.cyanconnode.connect.dto.LoginResponseDto;
import com.cyanconnode.connect.entity.Users;
import com.cyanconnode.connect.repository.UserRepository;
import com.cyanconnode.connect.utilities.JwtUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class AuthService
{

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Transactional
    public LoginResponseDto authUser(@Valid LoginRequestDto loginRequestDto)
    {
        Users user = userRepository.findByEmail(loginRequestDto.getEmail());

        if (user == null)
        {
            throw new BadCredentialsException("Incorrect username or password");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );

        if (!authentication.isAuthenticated())
        {
            throw new BadCredentialsException("Invalid credentials");

        }

        String token = jwtUtil.generateToken(Map.of(), loginRequestDto.getEmail());
        return new LoginResponseDto(token);
    }
}

