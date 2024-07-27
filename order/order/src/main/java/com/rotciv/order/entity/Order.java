package com.rotciv.order.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rotciv.order.enums.OrderEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "orders")
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private long total;

    @Column(name = "user_id")
    private String userId;

    private String description;


    @Enumerated(EnumType.STRING)
    private OrderEnum.OrderStatus status;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @OneToMany(mappedBy = "order")
    @JsonManagedReference
    private List<OrderDetail> orderDetails;

    @OneToOne()
    @JoinColumn(name = "payment_id")
    private PaymentDetail paymentDetail;
}
