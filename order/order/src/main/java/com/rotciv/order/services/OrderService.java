package com.rotciv.order.services;

import com.rotciv.order.dto.CreateOrderDto;
import com.rotciv.order.dto.OrderDto;
import com.rotciv.order.dto.PaymentDetailDto;
import com.rotciv.order.entity.Order;
import com.rotciv.order.entity.PaymentDetail;

public interface OrderService {
    Order createOrder(OrderDto orderDto);
    PaymentDetail createPaymentDetail(PaymentDetailDto paymentDetailDto);
    Order handleCreateOrder(CreateOrderDto createOrderDto);
    void updatePaymentStatus(String paymentId, String status);
    void deleteOrder(String orderId);
}
