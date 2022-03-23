package com.clone.coopang.controller;

import com.clone.coopang.domain.Product;
import com.clone.coopang.network.request.ProductRequest;
import com.clone.coopang.network.response.ProductResponse;
import com.clone.coopang.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /*
    todo postMapping 시 등록되어있는지 확인 후 이미 상품 존재시 validation 등록
    todo 상품 수정은 PutMapping으로 또한 상품 수정시에는 로그인한 사용자 id와 db가 맞는지 확인
    todo 상품 삭제 위와 동일
    todo 리뷰 점수는 상품 테이블에 리뷰 카운터를 두고 리뷰 등록시마다 (리뷰카운터 * 리뷰점수) + 신규 리뷰점수 / 리뷰카운터 + 1로 업데이트하고
    todo (리뷰카운터 * 리뷰점수)/리뷰카운터가 소수점 단위로 이루어지기에 총 리뷰 점수의 값과 차이가 날 수 있기 때문에 배치서버를 두고 리뷰점수 업데이트
     */

    @PostMapping("/product")
    public ResponseEntity<ProductResponse> register(@RequestBody ProductRequest productRequest) {
        Product product = productService.register(productRequest);
        ProductResponse productResponse = ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .userId(product.getUserId())
                .build();
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @PutMapping("/product")
    public ResponseEntity<ProductResponse> update(@RequestBody ProductRequest productRequest) {
        System.out.println(productRequest.getProductStock());
        Product product = productService.update(productRequest);
        ProductResponse productResponse = ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .userId(product.getUserId())
                .build();
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<ProductResponse> delete(@PathVariable Long productId){
        Product product = productService.delete(productId);
        ProductResponse productResponse = ProductResponse.builder().productId(product.getProductId()).build();
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }
    /*
        @GetMapping("/product/{userId}")
    public ResponseEntity<OrderResponse> userOrder(@PathVariable("userId") Long userId){
        List<ProductResponse> productResponse = productService.findUserOrder(userId);
        return new ResponseEntity(productResponse, HttpStatus.OK);
    }
     */

}
