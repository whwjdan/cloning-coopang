package com.clone.coopang.controller;

import com.clone.coopang.domain.Order;
import com.clone.coopang.domain.User;
import com.clone.coopang.network.request.OrderRequest;
import com.clone.coopang.network.request.SignUpRequest;
import com.clone.coopang.network.response.OrderResponse;
import com.clone.coopang.network.response.SignUpResponse;
import com.clone.coopang.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;

    @PostMapping("/order")
    public ResponseEntity<OrderResponse> save(@RequestBody OrderRequest orderRequest){

        User user = User.builder()
                .id(orderRequest.getUserId())
                .build();

        Order order = Order.builder()
                .user(user)
                .orderDate(orderRequest.getOrderDate())
                .orderStatus(orderRequest.isOrderStatus())
                .createdAt(orderRequest.getCreatedAt())
                .address(orderRequest.getAddress())
                .amount(orderRequest.getAmount())
                .build();

        Order orderReturn = orderRepository.save(order);

        OrderResponse orderResponse = OrderResponse.builder()
                .id(orderReturn.getId())
                .user_id(orderReturn.getUser().getId())
                .orderDate(orderReturn.getOrderDate())
                .orderStatus(orderReturn.isOrderStatus())
                .createdAt(orderReturn.getCreatedAt())
                .address(orderReturn.getAddress())
                .amount(orderReturn.getAmount())
                .build();

        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }
}
