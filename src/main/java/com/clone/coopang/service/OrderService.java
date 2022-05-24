package com.clone.coopang.service;

import com.clone.coopang.domain.*;
import com.clone.coopang.exception.ProductNotExistsException;
import com.clone.coopang.network.request.OrderRequest;
import com.clone.coopang.network.response.OrderResponse;
import com.clone.coopang.repository.OrderRepository;
import com.clone.coopang.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.clone.coopang.network.response.OrderResponse.ofOrderResponse;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

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
        orderItem.setOrderDetailStatus(OrderDetailStatus.ORDER);
        orderItem.setOrder(order);
    }

    @Transactional
    public OrderResponse order(OrderRequest orderRequest) {

        // 추후 상품 등록 및 검색 등 기능 완성 후 트랜잭션 시점에 상품 재고 확인하는 validation 여기에 추가
        Order returnOrder = orderRepository.save(createOrder(orderRequest));
        for(OrderItem orderItem : orderRequest.getOrderItems()){
            reduceProductStock(orderItem);
        }
        OrderResponse orderResponse = ofOrderResponse(returnOrder);
        return orderResponse;
    }

    // 전체주문삭제
    // todo 개별주문삭제 따로만들기
    // todo addProductStock validation 수정하기
    @Transactional
    public void cancelOrder(OrderRequest orderRequest){
        Optional<Order> order = orderRepository.findById(orderRequest.getId());
        order.ifPresent(selectOrder -> {
            selectOrder.setOrderStatus(OrderStatus.CANCEL);

            for (OrderItem orderItem : selectOrder.getOrderItems()){
                addProductStock(orderItem);
                orderItem.setOrderDetailStatus(OrderDetailStatus.CANCEL);
            }
            orderRepository.save(selectOrder);
        });
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

    private Product reduceProductStock(OrderItem orderItem) {
        Optional<Product> product = productRepository.findById(orderItem.getProductId());
        product.ifPresent(selectProduct -> {
            Product product1 = selectProduct.updateStock(selectProduct, false);
            productRepository.save(product1);
        });
        return product.orElseThrow(() -> new ProductNotExistsException("상품이 존재하지 않습니다."));
    }

    private Product addProductStock(OrderItem orderItem) {
        Optional<Product> product = productRepository.findById(orderItem.getProductId());
        product.ifPresent(selectProduct -> {
            Product product1 = selectProduct.updateStock(selectProduct, true);
            productRepository.save(product1);
            if(orderItem.getOrderDetailStatus() == OrderDetailStatus.CANCEL){
                throw new RuntimeException("aaaa");
            }
        });
        return product.orElseThrow(() -> new ProductNotExistsException("상품이 존재하지 않습니다."));
    }
}