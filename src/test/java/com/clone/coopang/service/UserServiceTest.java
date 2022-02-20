package com.clone.coopang.service;

import com.clone.coopang.domain.User;
import com.clone.coopang.network.request.UserApiRequest;
import com.clone.coopang.network.response.UserApiResponse;
import com.clone.coopang.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    User user = User.builder()
            .email("test@test.com")
            .password("test")
            .name("testName")
            .phoneNumber("010-0000-0000")
            .createdAt(LocalDateTime.now())
            .build();


    UserApiRequest request = UserApiRequest.builder()
            .email(user.getEmail())
            .password(user.getPassword())
            .name(user.getName())
            .phoneNumber(user.getPhoneNumber())
            .createdAt(user.getCreatedAt())
            .build();

    UserApiResponse response;

    @BeforeEach
    public void 사용자_객체_초기화(){
        user = User.builder()
                .email("test@test.com")
                .password("test")
                .name("testName")
                .phoneNumber("010-0000-0000")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    public void 회원_가입() {

        given(repository.save(any())).willReturn(user);
        response = service.createUser(request);
        assertThat(response.getEmail()).isEqualTo("test@test.com");

    }
}