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

    @Setter
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    private LocalDateTime orderDate;

    private int amount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus; //주문상태 [ORDER, CANCEL, PRTL_CNCL]

    public void addOrderDetail(OrderDetail orderDetail){
        orderDetails.add(orderDetail);
        orderDetail.setOrder(this);
    }
}
