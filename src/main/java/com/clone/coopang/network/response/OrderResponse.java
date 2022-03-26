package com.clone.coopang.network.response;

import com.clone.coopang.domain.Order;
import com.clone.coopang.domain.OrderDetail;
import com.clone.coopang.domain.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long id;

    private Long user_id;

    private List<OrderDetail> orderDetails = new ArrayList<>();

    private LocalDateTime orderDate;

    private OrderStatus orderStatus;

    private int amount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String address;

    public static OrderResponse ofOrderResponse(Order order){
        OrderResponse orderResponse = OrderResponse.builder()
                .id(order.getId())
                .user_id(order.getUser().getId())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .createdAt(order.getCreatedAt())
                .address(order.getAddress())
                .amount(order.getAmount())
                .orderDetails(order.getOrderDetails())
                .build();
        return orderResponse;
    }
}
