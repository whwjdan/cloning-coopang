package com.clone.coopang.network.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpResponse {

    private java.lang.Long id;

    private java.lang.String email;

    private java.lang.String password;

    private java.lang.String name;

    private java.lang.String phoneNumber;

    private java.lang.String address;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public SignUpResponse(java.lang.Long id, java.lang.String email, java.lang.String password, java.lang.String name, java.lang.String phoneNumber, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }

    @Builder
    public SignUpResponse(String email) {
        this.email = email;
    }
}
