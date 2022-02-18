package com.clone.coopang.service;

import com.clone.coopang.domain.User;
import com.clone.coopang.network.request.UserApiRequest;
import com.clone.coopang.network.response.UserApiResponse;
import com.clone.coopang.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserApiResponse createUser(UserApiRequest request) {
        User user = User.builder()
                .id(request.getId())
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .createdAt(LocalDateTime.now())
                .build();

        User newUser = userRepository.save(user);
        return response(newUser);
    }

    private UserApiResponse response(User user){
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .build();

        return userApiResponse;
    }

    private UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
