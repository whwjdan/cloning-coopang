package com.clone.coopang.repository;

import com.clone.coopang.domain.Product;
import com.clone.coopang.domain.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Transactional
    @Modifying
    @Query(value = "update Product p set p.productStatus = :productStatus " +
            "where p.productId = :productId")
    void updateDeleteStatus(@Param("productId") Long productId, @Param("productStatus") ProductStatus productStatus);
}
