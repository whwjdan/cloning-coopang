package com.clone.coopang.service;

import com.clone.coopang.domain.User;
import com.clone.coopang.exception.EmailDuplicateException;
import com.clone.coopang.network.request.SignUpRequest;
import com.clone.coopang.network.response.SignUpResponse;
import com.clone.coopang.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public SignUpResponse saveUser(SignUpRequest signUpRequest) {
        verifyEmail(signUpRequest.getEmail());

        User user = User.builder()
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .name(signUpRequest.getName())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .createdAt(LocalDateTime.now())
                .userRole(signUpRequest.getUserRole())
                .build();

        User signUpUser = userRepository.save(user);

        SignUpResponse signUpResponse = SignUpResponse.builder()
                .userRole(signUpUser.getUserRole())
                .id(signUpUser.getId())
                .build();

        return signUpResponse;
    }

    public void verifyEmail(String email){
        if (userRepository.existsByEmail(email)) {
            throw new EmailDuplicateException("이메일이 중복되었습니다. : " + email);
        }
    }
}