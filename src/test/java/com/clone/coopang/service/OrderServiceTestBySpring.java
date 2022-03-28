package com.clone.coopang.service;

import com.clone.coopang.CloningCoopangApplicationTests;
import com.clone.coopang.domain.*;
import com.clone.coopang.network.request.OrderRequest;
import com.clone.coopang.network.response.OrderResponse;
import com.clone.coopang.repository.OrderRepository;
import com.clone.coopang.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

@Rollback(value = false)
public class OrderServiceTestBySpring extends CloningCoopangApplicationTests {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    User user;
    Order order;
    OrderItem orderItem;
    OrderRequest orderRequest;
    OrderResponse orderResponse;

    @BeforeEach
    void 객체_초기화(){
        user = User.ofUser(1L);
        order = Order.builder()
                .id(1L)
                .build();

        User testUser = User.builder()
                .email("test@test.com")
                .name("testName")
                .password("testPWD")
                .createdAt(LocalDateTime.now())
                .build();

        User newUser = userRepository.save(testUser);


        orderItem = OrderItem.builder()
                .orderDetailStatus(OrderDetailStatus.ORDER)
                .deliveryStatus(DeliveryStatus.DELIVERED)
                .build();

        OrderItem orderItem2 = OrderItem.builder()
                .orderDetailStatus(OrderDetailStatus.ORDER)
                .deliveryStatus(DeliveryStatus.DELIVERED)
                .build();

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        orderItems.add(orderItem2);

        System.out.println("jsdafjkslafjklasdjflksaj");
        System.out.println(orderItems.get(0));
        System.out.println(orderItems.get(1));

        orderRequest = OrderRequest.builder()
                .id(1L)
                .userId(newUser.getId())
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .amount(1)
                .createdAt(LocalDateTime.now())
                .address("testAddress")
                .orderItems(orderItems)
                .build();
    }


    @Test
    void order() {
        //given

        //when
        orderResponse = orderService.order(orderRequest);
        //then
        for(OrderItem orderItem : orderResponse.getOrderItems()){
            System.out.println(orderItem.getId());
            System.out.println(orderItem.getOrderDetailStatus());
            System.out.println(orderItem.getOrder().getAddress());
        }
        List<OrderResponse> orderResponse2 = orderService.findUserOrder(1L);
        System.out.println(orderResponse2.get(0).getOrderItems().get(0).getId());
        System.out.println(orderResponse2.get(0).getOrderItems().get(1).getId());
    }

    @Test
    void findOrder() {
    }

    @Test
    void findUserOrder() {
    }

    @Test
    void entityToResponse() {
    }
}