package com.clone.coopang.network.response;

import com.clone.coopang.domain.OrderDetail;
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

    private boolean orderStatus;

    private int amount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String address;
}
