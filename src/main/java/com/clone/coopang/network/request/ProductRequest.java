package com.clone.coopang.network.request;

import com.clone.coopang.domain.Product;
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
public class ProductRequest {

    private Long productId;

    private Long userId;

    private String productName;

    private int productPrice;

    private int productStock;

    private double productScore;

    private ProductStatus productStatus;

    private RocketStatus rocket;

    private int deliveryPrice;

    public Product of(ProductRequest productRequest){
        Product product = Product.builder()
                .productId(productRequest.getProductId())
                .productPrice(productRequest.getProductPrice())
                .productName(productRequest.getProductName())
                .productScore(productRequest.getProductScore())
                .productStatus(productRequest.getProductStatus())
                .productStock(productRequest.getProductStock())
                .deliveryPrice(productRequest.getDeliveryPrice())
                .rocket(productRequest.getRocket())
                .userId(productRequest.getUserId())
                .build();

        return product;
    }

    public Product ofUpdate(ProductRequest productRequest){
        Product product = Product.builder()
                .productId(productRequest.getProductId())
                .productPrice(productRequest.getProductPrice())
                .productName(productRequest.getProductName())
                .productScore(productRequest.getProductScore())
                .productStatus(productRequest.getProductStatus())
                .productStock(productRequest.getProductStock())
                .deliveryPrice(productRequest.getDeliveryPrice())
                .rocket(productRequest.getRocket())
                .build();

        return product;
    }
}
