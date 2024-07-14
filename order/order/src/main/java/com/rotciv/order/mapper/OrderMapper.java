package com.rotciv.order.mapper;

import com.rotciv.order.dto.OrderDetailDto;
import com.rotciv.order.dto.OrderDto;
import com.rotciv.order.dto.PaymentDetailDto;
import com.rotciv.order.entity.Order;
import com.rotciv.order.entity.OrderDetail;
import com.rotciv.order.entity.PaymentDetail;

public class OrderMapper {
    public static Order dtoToEntity(OrderDto orderDto, Order order) {
        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setId(orderDto.getPayment_id());
        order.setUserId(orderDto.getUserId());
        order.setId(orderDto.getId());
        order.setDescription(orderDto.getDescription());
        order.setTotal(orderDto.getTotal());
        order.setShippingAddress(orderDto.getShippingAddress());
        return order;
    }

    public static OrderDetail orderDetailDtoToEntity(OrderDetailDto orderDetailDto, OrderDetail orderDetail) {
        orderDetail.setQuantity(orderDetailDto.getQuantity());
        orderDetail.setProductId(orderDetailDto.getProductId());
        Order order = new Order();
        order.setId(orderDetailDto.getOrderId());
        orderDetail.setOrder(order);
        orderDetail.setVariantId(orderDetailDto.getVariantId());
        return orderDetail;
    }

    public static PaymentDetail paymentDetailDtoToEntity(PaymentDetailDto paymentDetailDto, PaymentDetail paymentDetail) {
        paymentDetail.setId(paymentDetailDto.getId());
        paymentDetail.setAmount(paymentDetailDto.getAmount());
        paymentDetail.setStatus(paymentDetailDto.getStatus());
        paymentDetail.setType(paymentDetailDto.getType());
        return paymentDetail;
    }
}
