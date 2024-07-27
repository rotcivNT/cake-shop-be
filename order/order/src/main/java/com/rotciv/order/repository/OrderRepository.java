package com.rotciv.order.repository;

import com.rotciv.order.entity.Order;
import com.rotciv.order.enums.OrderEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String>{
    List<Order> findAllByUserId(String userId);
    List<Order> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    Page<Order> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
    Page<Order> findByCreatedAtBetweenAndStatus(LocalDateTime start, LocalDateTime end, OrderEnum.OrderStatus status, Pageable pageable);
    Page<Order> findByStatus(OrderEnum.OrderStatus status, Pageable pageable);
    Page<Order> findById(String id, Pageable pageable);
}
