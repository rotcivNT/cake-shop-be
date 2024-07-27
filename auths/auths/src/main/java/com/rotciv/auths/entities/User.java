package com.rotciv.auths.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter@Setter@ToString@AllArgsConstructor@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String password;

    @Column(name = "full_name")
    private String fullName;
    private String province;
    private String district;
    private String ward;

    @Column(name = "address_detail")
    private String addressDetail;
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_block")
    private boolean isBlock;

    @Column(name = "clerk_user_id")
    private String clerkUserId;
}
