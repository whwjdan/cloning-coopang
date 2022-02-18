package com.clone.coopang.network.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class UserApiResponse {
    private Long id;

    private String email;

    private String password;

    private String name;

    private String phoneNumber;

    private String address;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public UserApiResponse(Long id, String email, String password, String name, String phoneNumber, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }

    @Builder
    public UserApiResponse(Long id, String email, String password, String name, String phoneNumber, String address, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
