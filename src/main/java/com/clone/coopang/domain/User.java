package com.clone.coopang.domain;

/*
@Builder 어노테이션은 Builder 클래스를 자동으로 추가해준다.
setter 메서드 사용 시 객체의 일관성(consistency)가 보장될 수 없고,
immutable한 객체를 생성할 수 없다.

또한 build() 메서드를 통해 생성자 호출시 체이닝 패턴을 통해
각 객체의 의미를 파악할 수 있다.

ex) 빌더 패턴으로 객체 생성

    User user = User.builder()
            .id(1L)
            .account("user_test")
            .password("1234")
            .email("jjm@naver.com")
            .name("테스트")
            .phoneNumber("010-1111-1111")
            .build();

 */

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private String phoneNumber;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> order = new ArrayList<>();

    public static User ofUser(Long userId){
        User user = User.builder()
                .id(userId)
                .build();
        return user;
    }
}
