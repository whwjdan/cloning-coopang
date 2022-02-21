package com.clone.coopang.service;

import com.clone.coopang.domain.User;
import com.clone.coopang.network.request.SignUpRequest;
import com.clone.coopang.network.response.SignUpResponse;
import com.clone.coopang.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public SignUpResponse createUser(SignUpRequest request) {

        verifyEmail(request.getEmail());

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

    public void verifyEmail(String email){
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("duplicated email" + email);
        }
    }

    private SignUpResponse response(User user){
        SignUpResponse signUpResponse = SignUpResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

        return signUpResponse;
    }
}
