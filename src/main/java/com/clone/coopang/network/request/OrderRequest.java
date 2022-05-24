package com.clone.coopang.network.request;

import com.clone.coopang.domain.OrderItem;
import com.clone.coopang.domain.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    private Long id;

    private Long userId;

    private List<OrderItem> orderItems;

    private LocalDateTime orderDate;

    private OrderStatus orderStatus;

    private int amount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String address;

}
