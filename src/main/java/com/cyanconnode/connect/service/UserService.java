package com.cyanconnode.connect.service;

import com.cyanconnode.connect.dto.UserDto;
import com.cyanconnode.connect.entity.Users;
import com.cyanconnode.connect.mapper.UserMapper;
import com.cyanconnode.connect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public ResponseEntity<?> getUsers(int offset, int limit)
    {
        List<Users> users = userRepository.findAllWithOffsetLimit(offset, limit);

        if (users.isEmpty())
        {
            return ResponseEntity.ok("No Users found");
        }

        List<UserDto> userDtoList = users.stream()
                .map(userMapper::toDto)
                .toList();

        return ResponseEntity.ok(Map.of(
                "offset", offset,
                "limit", limit,
                "usersCount", userRepository.count(),
                "users", userDtoList
        ));
    }
}
