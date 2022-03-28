package com.clone.coopang.service;

import com.clone.coopang.domain.Order;
import com.clone.coopang.domain.OrderItem;
import com.clone.coopang.domain.User;
import com.clone.coopang.network.request.OrderRequest;
import com.clone.coopang.network.response.OrderResponse;
import com.clone.coopang.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.clone.coopang.network.response.OrderResponse.ofOrderResponse;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public static Order createOrder(OrderRequest orderRequest){
        User user = User.ofUser(orderRequest.getUserId());
        Order order = Order.builder()
                .user(user)
                .orderDate(orderRequest.getOrderDate())
                .orderStatus(orderRequest.getOrderStatus())
                .createdAt(orderRequest.getCreatedAt())
                .address(orderRequest.getAddress())
                .amount(orderRequest.getAmount())
                .orderItems(new ArrayList<>())
                .build();

        for(OrderItem orderItem : orderRequest.getOrderItems()){
            addOrderItem(order, orderItem);
        }
        return order;
    }

    private static void addOrderItem(Order order, OrderItem orderItem) {
        order.getOrderItems().add(orderItem);
        orderItem.setOrder(order);
    }

    @Transactional
    public OrderResponse order(OrderRequest orderRequest) {

        // 추후 상품 등록 및 검색 등 기능 완성 후 트랜잭션 시점에 상품 재고 확인하는 validation 여기에 추가
        Order orderReturn = orderRepository.save(createOrder(orderRequest));
        OrderResponse orderResponse = ofOrderResponse(orderReturn);
        return orderResponse;
    }

    public OrderResponse findOrder(Long orderId){
        Order order = orderRepository.findByOrderId(orderId);
        OrderResponse orderResponse = OrderResponse.builder()
                .orderItems(order.getOrderItems())
                .build();
        return orderResponse;
    }

    public List<OrderResponse> findUserOrder(Long userId){
        User user = User.ofUser(userId);
        List<Order> order = orderRepository.findByUserId(user);
        List<OrderResponse> orderResponses = new ArrayList<>();

        for(Order orders: order){
            OrderResponse orderResponse = OrderResponse.builder()
                    .id(orders.getId())
                    .orderItems(orders.getOrderItems())
                    .build();
            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }
}