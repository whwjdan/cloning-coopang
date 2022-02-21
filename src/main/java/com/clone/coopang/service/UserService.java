package com.clone.coopang.service;

import com.clone.coopang.domain.User;
import com.clone.coopang.network.request.UserApiRequest;
import com.clone.coopang.network.response.UserApiResponse;
import com.clone.coopang.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.DuplicateMappingException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserApiResponse createUser(UserApiRequest request) {

        boolean duplicateEmail = isExistsEmail(request.getEmail());

        if (duplicateEmail) {
            throw new RuntimeException("duplicated email" + request.getEmail());
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .createdAt(LocalDateTime.now())
                .build();

        User newUser = userRepository.save(user);
        return response(newUser);
    }

    public boolean isExistsEmail(String email){
        return userRepository.existsByEmail(email);
    }

    private UserApiResponse response(User user){
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

        return userApiResponse;
    }
}
