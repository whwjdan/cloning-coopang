package com.clone.coopang.repository;

import com.clone.coopang.domain.Order;
import com.clone.coopang.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select distinct o from Order o " +
            "join fetch o.user u " +
            "join fetch o.orderDetails od " +
            "where o.user = :user")
    List<Order> findByUserId(@Param("user") User user);

    @Query(value = "select distinct o from Order o " +
            "join fetch o.orderDetails od " +
            "where o.id = :orderId")
    Order findByOrderId(@Param("orderId") Long orderId);
}
