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

import static com.clone.coopang.network.response.OrderResponse.ofOrderResponse;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     *
     * @param orderRequest
     * @return
     */
    @Transactional
    public OrderResponse order(OrderRequest orderRequest) {
        Order order = Order.createOrder(orderRequest);
        // 추후 상품 등록 및 검색 등 기능 완성 후 트랜잭션 시점에 상품 재고 확인하는 validation 여기에 추가
        Order orderReturn = orderRepository.save(order);
        OrderResponse orderResponse = ofOrderResponse(orderReturn);
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
        User user = User.ofUser(userId);
        List<Order> order = orderRepository.findByUserId(user);
        List<OrderResponse> orderResponses = new ArrayList<>();

        for(Order orders: order){
            OrderResponse orderResponse = OrderResponse.builder()
                    .id(orders.getId())
                    .orderDetails(orders.getOrderDetails())
                    .build();
            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }
}