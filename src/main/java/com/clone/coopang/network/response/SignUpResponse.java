package com.clone.coopang.network.response;

import com.clone.coopang.domain.UserRole;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpResponse {

    private Long id;

    private String email;

    private String password;

    private String name;

    private String phoneNumber;

    private String address;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UserRole userRole;

    @Builder
    public SignUpResponse(Long id, String email, String password, String name, String phoneNumber, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }

    @Builder
    public SignUpResponse(String email, UserRole userRole) {
        this.email = email;
        this.userRole = userRole;
    }
}
