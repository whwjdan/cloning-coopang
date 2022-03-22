package com.clone.coopang.domain;

import com.clone.coopang.network.request.OrderRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    private LocalDateTime orderDate;

    private int amount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus; //주문상태 [ORDER, CANCEL, PRTL_CNCL]

    public void addOrderItem(OrderDetail orderItem) {
        orderDetails.add(orderItem);
        orderItem.setOrder(this);
    }

    public static Order createOrder(OrderRequest orderRequest) {
        User user = User.setUser(orderRequest.getUserId());

        Order order = Order.builder()
                .user(user)
                .orderDate(orderRequest.getOrderDate())
                .orderStatus(orderRequest.getOrderStatus())
                .createdAt(orderRequest.getCreatedAt())
                .address(orderRequest.getAddress())
                .amount(orderRequest.getAmount())
                .orderDetails(new ArrayList<>())
                .build();

        // cascade옵션으로 OrderDetailRepository를 만들지 않고 사용하려면 아래 메서드가 작동해야 하며,
        // 빌더패턴으로 적용 시 N에 대해 빈 객체를 생성해 주지 않으면 null에러가 반환된다.
        //order.addOrderItem(orderDetail);

        for(OrderDetail orderDetails:  orderRequest.getOrderDetails()){
            order.addOrderItem(orderDetails);
        }
        return order;
    }
}
