package com.rotciv.order.repository;

import com.rotciv.order.entity.OrderDetail;
import com.rotciv.order.entity.OrderDetailCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailCompositeKey>{
}
