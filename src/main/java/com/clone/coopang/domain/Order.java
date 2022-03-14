package com.clone.coopang.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //@OneToMany
    //private List<OrderDetail> orderDetails = new ArrayList<>();

    private LocalDateTime orderDate;

    private boolean orderStatus;

    private int amount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String address;
}
