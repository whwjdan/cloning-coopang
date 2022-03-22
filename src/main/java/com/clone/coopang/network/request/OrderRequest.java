package com.clone.coopang.network.request;

import com.clone.coopang.domain.OrderDetail;
import com.clone.coopang.domain.OrderStatus;
import com.clone.coopang.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    private Long id;

    private Long userId;

    private List<OrderDetail> orderDetails;

    private LocalDateTime orderDate;

    private OrderStatus orderStatus;

    private int amount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String address;

}
