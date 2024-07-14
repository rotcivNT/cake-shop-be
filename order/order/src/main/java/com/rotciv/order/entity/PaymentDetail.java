package com.rotciv.order.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_details")
@Getter@Setter@NoArgsConstructor
@AllArgsConstructor@ToString
public class PaymentDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private long amount;
    private String type;
    private String status;
}
