package com.rotciv.order.services.impl;

import com.rotciv.order.configuration.ServiceConfig;
import com.rotciv.order.dto.CartItemDto;
import com.rotciv.order.dto.ResponseCartItemDto;
import com.rotciv.order.dto.ResponseSessionDto;
import com.rotciv.order.dto.ShoppingDto;
import com.rotciv.order.entity.CartItem;
import com.rotciv.order.entity.ShoppingSession;
import com.rotciv.order.mapper.CartItemMapper;
import com.rotciv.order.mapper.ShoppingSessionMapper;
import com.rotciv.order.repository.CartItemRepository;
import com.rotciv.order.repository.ShoppingSessionRepository;
import com.rotciv.order.services.ShoppingService;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
public class ShoppingServiceImpl implements ShoppingService {
    private ShoppingSessionRepository shoppingSessionRepository;
    private CartItemRepository cartItemRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void handleShopping(ShoppingDto shoppingDto) {
        boolean isAdd = shoppingDto.getType().equals("add");
        int emptyCartItemCount = 0;
        String userId = shoppingDto.getShoppingSession().getUserId();
        ShoppingSession existSession = this.shoppingSessionRepository.findByUserId(userId);
        ShoppingSession shoppingSession = ShoppingSessionMapper.mapToShoppingSession(shoppingDto.getShoppingSession(), new ShoppingSession());

        shoppingSession.setUpdatedAt(LocalDateTime.now());
        if (existSession != null) {
            shoppingSession.setId(existSession.getId());
        }
        else {
            shoppingSession.setCreatedAt(LocalDateTime.now());
        }
        ShoppingSession createdSession = this.upsertShoppingSession(shoppingSession);
        if (!isAdd) {
            for (CartItemDto cartItemDto : shoppingDto.getCartItems()) {
                CartItem cartItem = CartItemMapper.mapToCartItem(cartItemDto, new CartItem());
                CartItem existCart = this.cartItemRepository.findByProductIdAndVariantIdAndShoppingSession(cartItem.getProductId(), cartItem.getVariantId(), createdSession);
                if (cartItem.getQuantity() == 0) {
                    this.cartItemRepository.delete(existCart);
                    emptyCartItemCount++;
                } else {
                    if (existCart == null) {
                        cartItem.setCreatedAt(LocalDateTime.now());
                    }
                    cartItem.setUpdatedAt(LocalDateTime.now());
                    cartItem.setShoppingSession(createdSession);
                    this.cartItemRepository.save(cartItem);
                }
            }
        }
        else {
            CartItemDto cartIDto = shoppingDto.getCartItems().getFirst();
            CartItem willAddCartItem = CartItemMapper.mapToCartItem(cartIDto, new CartItem());
            willAddCartItem.setShoppingSession(createdSession);
            CartItem cartI = this.cartItemRepository.findByProductIdAndVariantIdAndShoppingSession(willAddCartItem.getProductId(), willAddCartItem.getVariantId(), createdSession);
            willAddCartItem.setUpdatedAt(LocalDateTime.now());
            if (cartI != null) {
                cartI.setQuantity(cartI.getQuantity() + willAddCartItem.getQuantity());
                cartI.setUpdatedAt(LocalDateTime.now());
                this.cartItemRepository.save(cartI);
            }
            else {
                willAddCartItem.setCreatedAt(LocalDateTime.now());
                this.cartItemRepository.save(willAddCartItem);
            }

        }
        if (emptyCartItemCount == shoppingDto.getCartItems().size()) {
            this.shoppingSessionRepository.delete(createdSession);
        }
    }

    @Override
    public ShoppingSession upsertShoppingSession(ShoppingSession sSession) {
        return this.shoppingSessionRepository.save(sSession);
    }

    @Override
    public ResponseSessionDto getShoppingSessionsByUserId(String userId) throws BadRequestException {
        ShoppingSession session = this.shoppingSessionRepository.findByUserId(userId);
        if (session == null) {
            throw new BadRequestException();
        }
        ResponseSessionDto responseSessionDto = new ResponseSessionDto();
        responseSessionDto.setShoppingSession(session);
        List<CartItem> cartItems = session.getCartItems();
        List<ResponseCartItemDto> responseCartItemDtos = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            ResponseCartItemDto responseCartItemDto = new ResponseCartItemDto();

            responseCartItemDto.setSessionId(cartItem.getShoppingSession().getId());
            responseCartItemDto.setProductId(cartItem.getProductId());
            responseCartItemDto.setQuantity(cartItem.getQuantity());
            responseCartItemDto.setVariantId(cartItem.getVariantId());
            String productJson = this.restTemplate.getForObject(ServiceConfig.productServiceUrl  + "/products/get-product/" + cartItem.getProductId(), String.class);
            responseCartItemDto.setProductJson(productJson);

            responseCartItemDtos.add(responseCartItemDto);
        }
        responseSessionDto.setCartItems(responseCartItemDtos);
        return responseSessionDto;
    }

    @Override
    public void deleteShoppingSessionByUserId(String userId) {
        ShoppingSession session = this.shoppingSessionRepository.findByUserId(userId);
        if (session != null) {
            List<CartItem> cartItems = session.getCartItems();
            for (CartItem cartItem : cartItems) {
                this.cartItemRepository.delete(cartItem);
            }
            this.shoppingSessionRepository.delete(session);
        }
    }
}
