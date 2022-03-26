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
}
