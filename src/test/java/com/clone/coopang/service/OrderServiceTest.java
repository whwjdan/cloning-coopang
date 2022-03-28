package com.clone.coopang.service;

import com.clone.coopang.domain.*;
import com.clone.coopang.network.request.OrderRequest;
import com.clone.coopang.network.response.OrderResponse;
import com.clone.coopang.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest{

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService orderService;

    User user;
    Order order;
    OrderDetail orderDetail;
    OrderRequest orderRequest;
    OrderResponse orderResponse;
    List<Order> findByUserOrders = new ArrayList<>();

    @BeforeEach
    void 객체_초기화(){
        user = User.ofUser(1L);
        order = Order.builder()
                .id(10L)
                .build();

        orderDetail = OrderDetail.builder()
                .id(1L)
                .orderDetailStatus(OrderDetailStatus.ORDER)
                .deliveryStatus(DeliveryStatus.DELIVERED)
                .build();

        OrderDetail orderDetail2 = OrderDetail.builder()
                .id(2L)
                .orderDetailStatus(OrderDetailStatus.CANCEL)
                .deliveryStatus(DeliveryStatus.DELIVERED)
                .build();

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetail);
        orderDetails.add(orderDetail2);

        orderRequest = OrderRequest.builder()
                .id(10L)
                .userId(user.getId())
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .amount(1)
                .createdAt(LocalDateTime.now())
                .address("testAddress")
                .orderDetails(orderDetails)
                .build();

        order = OrderService.createOrder(orderRequest);

        order = Order.builder()
                .id(10L)
                .orderDetails(orderDetails)
                .build();
        findByUserOrders.add(order);
        order = Order.builder()
                .id(11L)
                .orderDetails(orderDetails)
                .build();
        findByUserOrders.add(order);

    }

    @Test
    void order1(){
        User user = User.ofUser(1L);
    }

    /*
    Mock으로 객체 생성시 spring context를 올려서 수행하는 것과 달리
    OneToMany에서 Many쪽에 id값이 자동으로 생성되지않는다. (autoincrement 옵션)
    따라서 테스트 시에 id값을 따로 생성해 주어야 하며, 혹은 Mock을 사용하지 않고 통합테스트를 수행해야한다.

    통합테스트 시 주의점은 Dto Request에 같은 값을 가지는 orderDetail 객체 여러 개를 List에 넣고 repository의 save()
    메서드 호출 시 같은 객체 값을 가지기 때문에 jpa에서 Many쪽에 데이터를 한번만 넣기 때문에 테스트 시에 주의가 필요하다.

    실제 restAPI로 테스트 시에는 @RequestBody로 값을 받아오면서 orderDetail안의 값이 모두 같더라도 새로운 객체를 생성해서
    할당해주기 때문에 OrderDetail 테이블의 id값이 정상적으로 생성되었다.

    order_detail쪽으로 넘어가는 쿼리의 개수만 확인했어도 쉽게 해결했을 문제였는데,
    Hibernate format_sql 옵션을 사용하지 않고 테스트를 했더니 문제점을 파악하는데 시간이 오래걸렸는데
    앞으로 테스트 시에도 해당 옵션을 키고 제대로 쿼리가 날아가는지 먼저 확인해봐야하겠다.

    List<OrderDetail> orderDetails = new ArrayList<>();
    orderDetails.add(orderDetail);
    orderDetails.add(orderDetail);

     */
    @Test
    @Transactional
    @DisplayName("주문을 한다.")
    void order() {
        //given
        given(orderRepository.save(any())).willReturn(order);
        //when
        orderResponse = orderService.order(orderRequest);
        //then
        assertThat(order.getOrderDetails().get(0).getOrderDetailStatus()).isEqualTo(OrderDetailStatus.ORDER);
        assertThat(orderResponse.getUser_id()).isEqualTo(1L);
        assertThat(orderResponse.getOrderDetails().get(0).getOrderDetailStatus()).isEqualTo(OrderDetailStatus.ORDER);
        int idx = 0;
        for(OrderDetail orderDetail : orderResponse.getOrderDetails()){

            assertThat(orderDetail.getOrderDetailStatus()).isEqualTo(orderRequest.getOrderDetails().get(idx).getOrderDetailStatus());
            idx++;
        }
        //verify
        then(orderRepository).should(times(1)).save(any());
    }

    @Test
    @DisplayName("주문 id로 주문을 찾는다.")
    void findOrder() {
        //given
        given(orderRepository.findByOrderId(any())).willReturn(order);
        //when
        orderResponse = orderService.findOrder(1L);
        //then
        int idx = 0;
        for(OrderDetail orderDetail : orderResponse.getOrderDetails()){
            assertThat(orderDetail.getId()).isEqualTo(orderRequest.getOrderDetails().get(idx).getId());
            assertThat(orderDetail.getOrderDetailStatus()).isEqualTo(orderRequest.getOrderDetails().get(idx).getOrderDetailStatus());
            assertThat(orderDetail.getDeliveryStatus()).isEqualTo(orderRequest.getOrderDetails().get(idx).getDeliveryStatus());
            idx++;
        }
        //verify
        then(orderRepository).should(times(1)).findByOrderId(any());
    }

    @Test
    @DisplayName("사용자 id로 사용자가 주문한 주문을 찾는다.")
    void findUserOrder() {
        List<OrderResponse> orderResponseList = new ArrayList<>();
        orderResponseList.add(new OrderResponse().builder().id(10L).build());
        orderResponseList.add(new OrderResponse().builder().id(11L).build());
        //given
        given(orderRepository.findByUserId(any())).willReturn(findByUserOrders);
        //when
        List<OrderResponse> orderResponse = orderService.findUserOrder(1L);
        //then
        int idx = 0;
        for (OrderResponse orderResponses : orderResponse){
            assertThat(orderResponses.getId()).isEqualTo(orderResponseList.get(idx).getId());
            idx++;
        }
        //verify
        then(orderRepository).should(times(1)).findByUserId(any());
    }
}