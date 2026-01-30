package com.cyanconnode.connect.service;

import com.cyanconnode.connect.dto.UserResponseDto;
import com.cyanconnode.connect.entity.Users;
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

    public ResponseEntity<Object> getUsers(String name, int offset, int limit)
    {
        List<Users> users = userRepository.getUsersQuery(name, offset, limit);

        if (users.isEmpty())
        {
            return ResponseEntity.ok(Map.of(
                    "usersCount", 0,
                    "users", List.of()
            ));
        }

        List<UserResponseDto> responseList = users.stream()
                .map(user -> UserResponseDto.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .username(user.getUserName())
                        .build())
                .toList();

        return ResponseEntity.ok(Map.of(
                "offset", offset,
                "limit", limit,
                "usersCount", userRepository.count(),
                "users", responseList
        ));
    }

}
