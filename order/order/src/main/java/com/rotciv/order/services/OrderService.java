package com.rotciv.order.services;

import com.rotciv.order.dto.*;
import com.rotciv.order.entity.Order;
import com.rotciv.order.entity.PaymentDetail;
import com.rotciv.order.enums.OrderEnum;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OrderService {
    Order createOrder(OrderDto orderDto);
    PaymentDetail createPaymentDetail(PaymentDetailDto paymentDetailDto);
    Order handleCreateOrder(CreateOrderDto createOrderDto);
    void updatePaymentStatus(String paymentId, OrderEnum.PaymentStatus status);
    void deleteOrder(String orderId);
    List<GetOrderDto> getOrders(String orderId);
    void updateOrderStatus(String orderId, OrderEnum.OrderStatus status);
    List<Order> filterOrders(FilterOrderDto filterOrderDto, int page, int size, String query);
    List<GetOrderDto> getOrdersByUserId(String userId);
    Map<String, List<Order>> getDailyStatistics();
    Map<String, List<Order>> getMonthlyStatistics();
    Map<String, List<Order>> getWeeklyStatistics();
    List<Order> getOrdersBetween(LocalDateTime start, LocalDateTime end);
}
