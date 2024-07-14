package com.rotciv.order.repository;

import com.rotciv.order.entity.CartItem;
import com.rotciv.order.entity.CartItemCompositeKey;
import com.rotciv.order.entity.ShoppingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemCompositeKey>{
    CartItem findByProductIdAndVariantIdAndShoppingSession(String productId, String variantId, ShoppingSession shoppingSession);
}
