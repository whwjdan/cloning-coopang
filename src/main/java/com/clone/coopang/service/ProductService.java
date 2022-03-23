package com.clone.coopang.service;

import com.clone.coopang.domain.Product;
import com.clone.coopang.domain.ProductStatus;
import com.clone.coopang.exception.ProductExistsException;
import com.clone.coopang.exception.ProductNotExistsException;
import com.clone.coopang.network.request.ProductRequest;
import com.clone.coopang.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product register(ProductRequest productRequest){
        Product product = productRequest.of(productRequest);
        isExistsProduct(product);
        return productRepository.save(product);
    }

    public Product update(ProductRequest productRequest){
        Product product = productRequest.ofUpdate(productRequest);
        Optional<Product> updateProduct = productRepository.findById(product.getProductId());
        updateProduct.ifPresent(selectProduct ->{
            productRequest.ofUpdate(productRequest);
            productRepository.save(selectProduct);
                });
        return updateProduct.orElseThrow(() -> new ProductNotExistsException("상품이 존재하지 않습니다."));
    }

    public Product delete(Long productId){
        Optional<Product> product = productRepository.findById(productId);

        //productRepository.updateDeleteStatus(product.getProductId(), ProductStatus.CLOSED);
        product.ifPresent(selectProduct ->{
                Product saveProduct = selectProduct.ofProductStatus(selectProduct, ProductStatus.CLOSED);
            productRepository.save(saveProduct);
        });
        return product.orElseThrow(() -> new ProductNotExistsException("삭제하려는 사움이 존재하지 않습니다. "));
    }

    public void isExistsProduct(Product product){
        if(productRepository.findById(product.getProductId()).isPresent() ){
            throw new ProductExistsException("이미 등록되어있는 상품입니다. " + product.getProductId());
        }
    }
}
