package com.clone.coopang.service;

import com.clone.coopang.domain.User;
import com.clone.coopang.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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

    @Test
    public void createUser() {

        given(repository.save(any())).willReturn(user);

    }
}