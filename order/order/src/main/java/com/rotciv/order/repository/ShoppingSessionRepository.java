package com.rotciv.order.repository;

import com.rotciv.order.entity.ShoppingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingSessionRepository extends JpaRepository<ShoppingSession, String>{
    ShoppingSession findByUserId(String userId);
}
