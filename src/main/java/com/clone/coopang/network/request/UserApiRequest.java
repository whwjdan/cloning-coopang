package com.clone.coopang.network.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserApiRequest {

    private Long id;

    private String email;

    private String password;

    private String name;

    private String phoneNumber;

    private String address;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
