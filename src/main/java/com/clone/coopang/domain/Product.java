package com.clone.coopang.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long productId;
    
    private Long userId;

    private String productName;

    private int productPrice;

    private int productStock;

    private double productScore;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @Enumerated(EnumType.STRING)
    private RocketStatus rocket;

    private int deliveryPrice;

    public static Product ofProductStatus(Product product, ProductStatus productStatus){
        Product returnProduct = Product.builder()
                .productStatus(productStatus)
                .userId(product.getUserId())
                .productId(product.getProductId())
                .rocket(product.getRocket())
                .productStock(product.getProductStock())
                .productScore(product.getProductScore())
                .deliveryPrice(product.getDeliveryPrice())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .build();

        return returnProduct;
    }

    public Product updateStock(Product product, boolean isStockPlus){
        Product returnProduct;
        if(isStockPlus){
            returnProduct = Product.builder()
                    .productPrice(product.getProductPrice())
                    .productName(product.getProductName())
                    .productScore(product.getProductScore())
                    .productStock(product.getProductStock()+1)
                    .deliveryPrice(product.getDeliveryPrice())
                    .productId(product.getProductId())
                    .rocket(product.getRocket())
                    .userId(product.getUserId())
                    .productStatus(product.getProductStatus())
                    .build();
        } else {
            returnProduct = Product.builder()
                    .productPrice(product.getProductPrice())
                    .productName(product.getProductName())
                    .productScore(product.getProductScore())
                    .productStock(product.getProductStock()-1)
                    .deliveryPrice(product.getDeliveryPrice())
                    .productId(product.getProductId())
                    .rocket(product.getRocket())
                    .userId(product.getUserId())
                    .productStatus(product.getProductStatus())
                    .build();
        }
        return returnProduct;
    }
}
