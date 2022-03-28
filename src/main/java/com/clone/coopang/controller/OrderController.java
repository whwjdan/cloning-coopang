package com.clone.coopang.controller;

import com.clone.coopang.network.request.OrderRequest;
import com.clone.coopang.network.response.OrderResponse;
import com.clone.coopang.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<OrderResponse> save(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.order(orderRequest);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

    @PostMapping("/order/cancel")
    public ResponseEntity<OrderResponse> cancel(@RequestBody OrderRequest orderRequest) {
        orderService.cancelOrder(orderRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/order/user/{userId}")
    public ResponseEntity<OrderResponse> userOrder(@PathVariable("userId") Long userId){
        List<OrderResponse> orderResponse = orderService.findUserOrder(userId);
        return new ResponseEntity(orderResponse, HttpStatus.OK);
    }

    @GetMapping("/order/list/{orderId}")
    public ResponseEntity<OrderResponse> order(@PathVariable("orderId") Long orderId){
        OrderResponse orderResponse = orderService.findOrder(orderId);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }
}
