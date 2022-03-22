package com.clone.coopang.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "order_id")
    private Order order;

    private Long productId;

    private int productPrice;

    private int deliveryPrice;

    private int salePrice;

    private int totalPrice;

    private int paymentPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_detail_status")
    private OrderDetailStatus orderDetailStatus; //주문상태 [ORDER, CANCEL]

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    private String thumbnail;

    public void setOrder(Order order){
        this.order = order;
    }
}
