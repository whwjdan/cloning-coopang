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
    OrderDetail orderDetail;
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


        orderDetail = OrderDetail.builder()
                .orderDetailStatus(OrderDetailStatus.ORDER)
                .deliveryStatus(DeliveryStatus.DELIVERED)
                .build();

        OrderDetail orderDetail2 = OrderDetail.builder()
                .orderDetailStatus(OrderDetailStatus.ORDER)
                .deliveryStatus(DeliveryStatus.DELIVERED)
                .build();

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetail);
        orderDetails.add(orderDetail2);

        System.out.println("jsdafjkslafjklasdjflksaj");
        System.out.println(orderDetails.get(0));
        System.out.println(orderDetails.get(1));

        orderRequest = OrderRequest.builder()
                .id(1L)
                .userId(newUser.getId())
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .amount(1)
                .createdAt(LocalDateTime.now())
                .address("testAddress")
                .orderDetails(orderDetails)
                .build();
    }


    @Test
    void order() {
        //given

        //when
        orderResponse = orderService.order(orderRequest);
        //then
        for(OrderDetail orderDetail : orderResponse.getOrderDetails()){
            System.out.println(orderDetail.getId());
            System.out.println(orderDetail.getOrderDetailStatus());
            System.out.println(orderDetail.getOrder().getAddress());
        }
        List<OrderResponse> orderResponse2 = orderService.findUserOrder(1L);
        System.out.println(orderResponse2.get(0).getOrderDetails().get(0).getId());
        System.out.println(orderResponse2.get(0).getOrderDetails().get(1).getId());
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