package com.rotciv.order.controllers;

import com.rotciv.order.dto.CreateOrderDto;
import com.rotciv.order.dto.PostPaymentDto;
import com.rotciv.order.dto.ResponseDto;
import com.rotciv.order.dto.ShoppingDto;
import com.rotciv.order.entity.Order;
import com.rotciv.order.services.OrderService;
import com.rotciv.order.services.ShoppingService;
import com.rotciv.order.services.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(path = "/v1/api/shopping", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class ShoppingSessionController {
    private ShoppingService shoppingService;
    private VNPayService vnPayService;
    private OrderService orderService;

    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestBody ShoppingDto shoppingDto) {
        try {
            this.shoppingService.handleShopping(shoppingDto);
            return ResponseEntity.ok().body("Successfully added to cart");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-cart/{userId}")
    public ResponseEntity<?> getCart(@PathVariable String userId) {
        try {
            return ResponseEntity.ok().body(this.shoppingService.getShoppingSessionsByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create-payment")
    public ResponseEntity<?> createVNPPayment(HttpServletRequest req, @RequestBody CreateOrderDto createOrderDto) throws UnsupportedEncodingException {
        Order order = this.orderService.handleCreateOrder(createOrderDto);
        if (createOrderDto.getPaymentDetail().getType().equals("vnpay")) {
            return ResponseEntity.ok().body(this.vnPayService.createPayment(req, order.getId(), order.getPaymentDetail().getId()));
        }
        return ResponseEntity.ok().body(new ResponseDto("201", "Successfully created order"));
    }

    @PostMapping("/post-payment")
    public ResponseEntity<?> postPayment(@RequestBody PostPaymentDto postPaymentDto) {
        try {
            String resVnPayCode = postPaymentDto.getResVnPayCode();
            if (resVnPayCode.isEmpty()) {
                this.shoppingService.deleteShoppingSessionByUserId(postPaymentDto.getUserId());
            } else if (resVnPayCode.equals("00")) {
                this.orderService.updatePaymentStatus(postPaymentDto.getPaymentId(), "SUCCESS");
                this.shoppingService.deleteShoppingSessionByUserId(postPaymentDto.getUserId());
            } else if (resVnPayCode.equals("24")) {
                this.orderService.deleteOrder(postPaymentDto.getOrderId());
            }
            return ResponseEntity.ok().body(new ResponseDto("200", "Successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
