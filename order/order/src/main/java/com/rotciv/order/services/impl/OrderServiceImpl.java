package com.rotciv.order.services.impl;

import com.rotciv.order.dto.CreateOrderDto;
import com.rotciv.order.dto.OrderDetailDto;
import com.rotciv.order.dto.OrderDto;
import com.rotciv.order.dto.PaymentDetailDto;
import com.rotciv.order.entity.Order;
import com.rotciv.order.entity.OrderDetail;
import com.rotciv.order.entity.PaymentDetail;
import com.rotciv.order.mapper.OrderMapper;
import com.rotciv.order.repository.OrderDetailRepository;
import com.rotciv.order.repository.OrderRepository;
import com.rotciv.order.repository.PaymentDetailRepository;
import com.rotciv.order.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private PaymentDetailRepository paymentDetailRepository;

    @Override
    public Order handleCreateOrder(CreateOrderDto createOrderDto) {
        Order createdOrder = createOrder(createOrderDto.getOrder());
        if (!createdOrder.getId().isEmpty()) {
            try {
                List<OrderDetailDto> orderDetails = createOrderDto.getOrderDetails();
                for (OrderDetailDto orderDetailDto : orderDetails) {
                    OrderDetail orderDetail = OrderMapper.orderDetailDtoToEntity(orderDetailDto, new OrderDetail());

                    orderDetail.setOrder(createdOrder);
                    orderDetail.setCreatedAt(LocalDateTime.now());
                    orderDetail.setUpdatedAt(LocalDateTime.now());
                    this.orderDetailRepository.save(orderDetail);
                }
                PaymentDetailDto paymentDetailDto = createOrderDto.getPaymentDetail();
                PaymentDetail pd = this.createPaymentDetail(paymentDetailDto);
                createdOrder.setPaymentDetail(pd);
                this.orderRepository.save(createdOrder);

            } catch (Exception e) {
                throw new RuntimeException("Error");
            }
        }
        return createdOrder;
    }

    @Override
    public void updatePaymentStatus(String paymentId, String status) {
        PaymentDetail paymentDetail = this.paymentDetailRepository.findById(paymentId).orElse(null);
        if (paymentDetail != null) {
            paymentDetail.setStatus(status);
            this.paymentDetailRepository.save(paymentDetail);
        }
    }

    @Override
    public void deleteOrder(String orderId) {
        Order order = this.orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            List<OrderDetail> orderDetails = order.getOrderDetails();
            PaymentDetail paymentDetail = order.getPaymentDetail();
            for (OrderDetail orderDetail : orderDetails) {
                this.orderDetailRepository.delete(orderDetail);
            }
            this.orderRepository.delete(order);
            if (paymentDetail != null) {
                this.paymentDetailRepository.delete(paymentDetail);
            }
        }
    }

    @Override
    public Order createOrder(OrderDto orderDto) {
        Order order = OrderMapper.dtoToEntity(orderDto, new Order());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        try {
            return this.orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException("Error");
        }
    }

    @Override
    public PaymentDetail createPaymentDetail(PaymentDetailDto paymentDetailDto) {
        try {
            PaymentDetail paymentDetail = OrderMapper.paymentDetailDtoToEntity(paymentDetailDto, new PaymentDetail());
            paymentDetail.setCreatedAt(LocalDateTime.now());
            paymentDetail.setUpdatedAt(LocalDateTime.now());
            return this.paymentDetailRepository.save(paymentDetail);
        } catch (Exception e) {
            throw new RuntimeException("Error");
        }
    }

}
