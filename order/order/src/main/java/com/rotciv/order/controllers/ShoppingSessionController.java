package com.rotciv.order.controllers;

import com.rotciv.order.dto.*;
import com.rotciv.order.entity.Order;
import com.rotciv.order.enums.OrderEnum;
import com.rotciv.order.services.OrderService;
import com.rotciv.order.services.ShoppingService;
import com.rotciv.order.services.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

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
            return ResponseEntity.ok().body(this.vnPayService.createPayment(req, order.getId(), order.getPaymentDetail().getId(), order.getTotal()));
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
                this.orderService.updatePaymentStatus(postPaymentDto.getPaymentId(), OrderEnum.PaymentStatus.PAID);
                this.shoppingService.deleteShoppingSessionByUserId(postPaymentDto.getUserId());
            } else if (resVnPayCode.equals("24")) {
                this.orderService.deleteOrder(postPaymentDto.getOrderId());
            }
            return ResponseEntity.ok().body(new ResponseDto("200", "Successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-orders/{orderId}")
    public ResponseEntity<?> getAllOrder(@PathVariable String orderId) {
        try {
            return ResponseEntity.ok().body(this.orderService.getOrders(orderId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update-order/{orderId}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String orderId, @RequestBody OrderEnum.OrderStatus status) {
        try {
            this.orderService.updateOrderStatus(orderId, status);
            return ResponseEntity.ok().body(new ResponseDto("200", "Successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/filter-order")
    public ResponseEntity<?> filterOrder(@RequestBody FilterOrderDto filterOrderDto, @RequestParam int page, @RequestParam int size, @RequestParam(required = false) String q) {
        try {
            q = q == null ? "" : q;
            return ResponseEntity.ok().body(this.orderService.filterOrders(filterOrderDto, page, size, q));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-orders-by-user/{userId}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable String userId) {
        try {
            return ResponseEntity.ok().body(this.orderService.getOrdersByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-statistics")
    public ResponseEntity<Map<String, List<Order>>> getStatistics(@RequestParam String type) {
        Map<String, List<Order>> statistics;
        switch (type) {
            case "daily":
                statistics = orderService.getDailyStatistics();
                break;
            case "weekly":
                statistics = orderService.getWeeklyStatistics();
                break;
            case "monthly":
                statistics = orderService.getMonthlyStatistics();
                break;
            default:
                return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(statistics);
    }
}
