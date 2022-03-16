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
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    private Long productId;

    private int productPrice;

    private int deliveryPrice;

    private int salePrice;

    private int totalPrice;

    private int paymentPrice;

    private String thumbnail;

    public void setOrder(Order order){
        this.order = order;
    }
}
