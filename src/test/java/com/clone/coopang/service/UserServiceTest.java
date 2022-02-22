package com.clone.coopang.service;

import com.clone.coopang.domain.User;
import com.clone.coopang.exception.EmailDuplicateException;
import com.clone.coopang.network.request.SignUpRequest;
import com.clone.coopang.network.response.SignUpResponse;
import com.clone.coopang.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    User user;

    SignUpRequest signUpRequest;

    SignUpResponse SignUpResponse;

    @BeforeEach
    void 사용자_객체_초기화(){
        user = User.builder()
                .email("test@test.com")
                .password("test")
                .name("testName")
                .phoneNumber("010-1111-0000")
                .createdAt(LocalDateTime.now())
                .build();

        signUpRequest = SignUpRequest.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Test
    void 회원_가입() {
        //given
        given(userRepository.save(any())).willReturn(user);
        //when
        SignUpResponse = userService.saveUser(signUpRequest);
        //then
        assertThat(SignUpResponse.getEmail()).isEqualTo("test@test.com");
        //verify
        then(userRepository).should(times(1)).save(any());
    }

    @Test
    void 이메일_중복체크() {
        //given
        given(userRepository.existsByEmail(signUpRequest.getEmail())).willReturn(false);
        //when
        userService.verifyEmail(signUpRequest.getEmail());
        //then
        // doNothing

        //verify
        then(userRepository).should(times(1)).existsByEmail(anyString());
    }

    @Test
    void 이메일_중복체크_예외발생() throws EmailDuplicateException {
        //given
        given(userRepository.save(any())).willThrow(EmailDuplicateException.class);

        //when, then
        assertThrows(EmailDuplicateException.class, () -> {
            userService.saveUser(signUpRequest);
        });

        //verify
        then(userRepository).should(times(1)).existsByEmail(anyString());
    }
}