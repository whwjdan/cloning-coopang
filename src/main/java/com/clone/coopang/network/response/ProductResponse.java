package com.clone.coopang.network.response;

import com.clone.coopang.domain.ProductStatus;
import com.clone.coopang.domain.RocketStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {

    private Long productId;

    private Long userId;

    private String productName;

    private int productPrice;

    private int productStock;

    private double productScore;

    private ProductStatus productStatus;

    private RocketStatus rocket;

    private int deliveryPrice;
}
