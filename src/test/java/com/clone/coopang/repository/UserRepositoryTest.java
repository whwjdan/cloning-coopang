package com.clone.coopang.repository;

import com.clone.coopang.CloningCoopangApplicationTests;
import com.clone.coopang.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


public class UserRepositoryTest extends CloningCoopangApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void 회원가입(){
        User testUser = User.builder()
                .email("test@test.com")
                .name("testName")
                .password("testPWD")
                .createdAt(LocalDateTime.now())
                .build();

        User newUser = userRepository.save(testUser);
        assertThat(newUser).isNotNull();
        assertThat(newUser.getEmail()).isEqualTo(userRepository.findByEmail("test@test.com").getEmail());

    }



}