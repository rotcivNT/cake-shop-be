package com.rotciv.order.services.impl;

import com.rotciv.order.configuration.ServiceConfig;
import com.rotciv.order.dto.*;
import com.rotciv.order.entity.Order;
import com.rotciv.order.entity.OrderDetail;
import com.rotciv.order.entity.PaymentDetail;
import com.rotciv.order.enums.OrderEnum;
import com.rotciv.order.mapper.OrderMapper;
import com.rotciv.order.repository.OrderDetailRepository;
import com.rotciv.order.repository.OrderRepository;
import com.rotciv.order.repository.PaymentDetailRepository;
import com.rotciv.order.services.OrderService;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@AllArgsConstructor
@DynamicUpdate
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private PaymentDetailRepository paymentDetailRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public Order handleCreateOrder(CreateOrderDto createOrderDto) {
        OrderDto data = createOrderDto.getOrder();
        Order createdOrder = createOrder(data);

        System.out.println("createdOrder: " + createdOrder);
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
    public void updatePaymentStatus(String paymentId, OrderEnum.PaymentStatus status) {
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
    public List<GetOrderDto> getOrders(String orderId) {
        List<Order> orders;
        if (orderId.isEmpty()) {
            orders = this.orderRepository.findAll();
        } else {
            orders = List.of(Objects.requireNonNull(this.orderRepository.findById(orderId).orElse(null)));
        }
//        handle lai
        List<GetOrderDto> results = new ArrayList<>();
        for (Order order : orders) {
            GetOrderDto getOrderDto = new GetOrderDto();
            getOrderDto.setId(order.getId());
            getOrderDto.setTotal(order.getTotal());
            getOrderDto.setCreatedAt(order.getCreatedAt());
            getOrderDto.setUpdatedAt(order.getUpdatedAt());
            getOrderDto.setDescription(order.getDescription());
            getOrderDto.setPaymentDetail(order.getPaymentDetail());
            getOrderDto.setShippingAddress(order.getShippingAddress());
            getOrderDto.setStatus(order.getStatus());
            String userJson = this.restTemplate.getForObject(ServiceConfig.authServiceUrl + "/user/" + order.getUserId(), String.class);
            getOrderDto.setUserJson(userJson);
            List<ResponseOrderDetailDto> responseOrderDetailDtos = new ArrayList<>();
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                ResponseOrderDetailDto responseOrderDetailDto = new ResponseOrderDetailDto();
                String productJson = this.restTemplate.getForObject(ServiceConfig.productServiceUrl + "/products/get-product/" + orderDetail.getProductId(), String.class);
                responseOrderDetailDto.setOrderId(orderDetail.getOrder().getId());
                responseOrderDetailDto.setUpdatedAt(orderDetail.getUpdatedAt());
                responseOrderDetailDto.setCreatedAt(orderDetail.getCreatedAt());
                responseOrderDetailDto.setQuantity(orderDetail.getQuantity());
                responseOrderDetailDto.setProductId(orderDetail.getProductId());
                responseOrderDetailDto.setVariantId(orderDetail.getVariantId());
                responseOrderDetailDto.setProductJson(productJson);
                responseOrderDetailDtos.add(responseOrderDetailDto);
                responseOrderDetailDto.setPrice(orderDetail.getPrice());
            }
            getOrderDto.setOrderDetailsFull(responseOrderDetailDtos);
            results.add(getOrderDto);
        }
        return results;
    }

    @Override
    public void updateOrderStatus(String orderId, OrderEnum.OrderStatus status) {
        Order order = this.orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(status);
            this.orderRepository.save(order);
        }
    }

    @Override
    public List<Order> filterOrders(FilterOrderDto filterOrderDto, int page, int size, String query) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<Order> orders;
        List<Order> result = new ArrayList<>();
        OrderEnum.OrderStatus status = filterOrderDto.getStatus();
        if (query.isEmpty()) {
            if (filterOrderDto.getStartedAt() != null && filterOrderDto.getEndedAt() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(filterOrderDto.getStartedAt(), formatter);
                LocalDateTime startDate = date.atStartOfDay();
                date = LocalDate.parse(filterOrderDto.getEndedAt(), formatter);
                LocalDateTime endDate = date.atStartOfDay();
                if (status != null) {
                    orders = this.orderRepository.findByCreatedAtBetweenAndStatus(startDate, endDate, status, pageable);
                } else {
                    orders = this.orderRepository.findByCreatedAtBetween(startDate, endDate, pageable);
                }
            } else if (status == null) {
                orders = this.orderRepository.findAll(pageable);
            } else {
                orders = this.orderRepository.findByStatus(status, pageable);
            }
        } else {
            orders = this.orderRepository.findById(query, pageable);
        }
        if (orders.hasContent()) {
            result = orders.getContent();
        }
        return result;
    }

    @Override
    public List<GetOrderDto> getOrdersByUserId(String userId) {
        List<Order> orders = this.orderRepository.findAllByUserId(userId);
        List<GetOrderDto> results = new ArrayList<>();
        for (Order order : orders) {
            GetOrderDto getOrderDto = new GetOrderDto();
            getOrderDto.setId(order.getId());
            getOrderDto.setTotal(order.getTotal());
            getOrderDto.setCreatedAt(order.getCreatedAt());
            getOrderDto.setUpdatedAt(order.getUpdatedAt());
            getOrderDto.setDescription(order.getDescription());
            getOrderDto.setPaymentDetail(order.getPaymentDetail());
            getOrderDto.setShippingAddress(order.getShippingAddress());
            getOrderDto.setStatus(order.getStatus());
            String userJson = this.restTemplate.getForObject(ServiceConfig.authServiceUrl + "/user/" + order.getUserId(), String.class);
            getOrderDto.setUserJson(userJson);
            List<ResponseOrderDetailDto> responseOrderDetailDtos = new ArrayList<>();
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                ResponseOrderDetailDto responseOrderDetailDto = new ResponseOrderDetailDto();
                String productJson = this.restTemplate.getForObject(ServiceConfig.productServiceUrl + "/products/get-product/" + orderDetail.getProductId(), String.class);
                responseOrderDetailDto.setOrderId(orderDetail.getOrder().getId());
                responseOrderDetailDto.setUpdatedAt(orderDetail.getUpdatedAt());
                responseOrderDetailDto.setCreatedAt(orderDetail.getCreatedAt());
                responseOrderDetailDto.setQuantity(orderDetail.getQuantity());
                responseOrderDetailDto.setProductId(orderDetail.getProductId());
                responseOrderDetailDto.setVariantId(orderDetail.getVariantId());
                responseOrderDetailDto.setProductJson(productJson);
                responseOrderDetailDtos.add(responseOrderDetailDto);
                responseOrderDetailDto.setPrice(orderDetail.getPrice());
            }
            getOrderDto.setOrderDetailsFull(responseOrderDetailDtos);
            results.add(getOrderDto);
        }
        return results;
    }


    @Override
    public List<Order> getOrdersBetween(LocalDateTime start, LocalDateTime end) {
        return orderRepository.findByCreatedAtBetween(start, end);
    }

    @Override
    public Map<String, List<Order>> getDailyStatistics() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime today = now.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime yesterday = today.minusDays(1);

        List<Order> todayOrders = getOrdersBetween(today, now);
        List<Order> yesterdayOrders = getOrdersBetween(yesterday, today);

        Map<String, List<Order>> result = new HashMap<>();
        result.put("today", todayOrders);
        result.put("yesterday", yesterdayOrders);
        return result;
    }

    @Override
    public Map<String, List<Order>> getMonthlyStatistics() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime thisWeekStart = now.withHour(0).withMinute(0).withSecond(0).withNano(0)
                .minusDays(now.getDayOfWeek().getValue() - 1);
        LocalDateTime lastWeekStart = thisWeekStart.minusWeeks(1);

        List<Order> thisWeekOrders = getOrdersBetween(thisWeekStart, now);
        List<Order> lastWeekOrders = getOrdersBetween(lastWeekStart, thisWeekStart);

        Map<String, List<Order>> result = new HashMap<>();
        result.put("thisWeek", thisWeekOrders);
        result.put("lastWeek", lastWeekOrders);
        return result;
    }

    @Override
    public Map<String, List<Order>> getWeeklyStatistics() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime thisMonthStart = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime lastMonthStart = thisMonthStart.minusMonths(1);

        List<Order> thisMonthOrders = getOrdersBetween(thisMonthStart, now);
        List<Order> lastMonthOrders = getOrdersBetween(lastMonthStart, thisMonthStart);

        Map<String, List<Order>> result = new HashMap<>();
        result.put("thisMonth", thisMonthOrders);
        result.put("lastMonth", lastMonthOrders);
        return result;
    }

    @Override
    public Order createOrder(OrderDto orderDto) {
        Order order = OrderMapper.dtoToEntity(orderDto, new Order());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setStatus(OrderEnum.OrderStatus.PENDING);
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
