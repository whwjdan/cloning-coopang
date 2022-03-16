package com.clone.coopang.service;

import com.clone.coopang.domain.Order;
import com.clone.coopang.domain.User;
import com.clone.coopang.network.request.OrderRequest;
import com.clone.coopang.network.response.OrderResponse;
import com.clone.coopang.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponse order(OrderRequest orderRequest) {
        Order order = Order.createOrder(orderRequest);
        Order orderReturn = orderRepository.save(order);
        OrderResponse orderResponse = entityToResponse(orderReturn);
        return orderResponse;
    }

    public OrderResponse findOrder(Long orderId){
        Order order = orderRepository.findByOrderId(orderId);
        OrderResponse orderResponse = OrderResponse.builder()
                .orderDetails(order.getOrderDetails())
                .build();
        return orderResponse;
    }

    public List<OrderResponse> findUserOrder(Long userId){
        User user = User.setUser(userId);
        List<Order> order = orderRepository.findByUserId(user);
        List<OrderResponse> orderResponses = new ArrayList<>();

        for(Order orders: order){
            OrderResponse orderResponse = OrderResponse.builder()
                    .orderDetails(orders.getOrderDetails())
                    .build();
            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }

    public OrderResponse entityToResponse(Order order){
        OrderResponse orderResponse = OrderResponse.builder()
                .id(order.getId())
                .user_id(order.getUser().getId())
                .orderDate(order.getOrderDate())
                .orderStatus(order.isOrderStatus())
                .createdAt(order.getCreatedAt())
                .address(order.getAddress())
                .amount(order.getAmount())
                .build();
        return orderResponse;
    }
}
