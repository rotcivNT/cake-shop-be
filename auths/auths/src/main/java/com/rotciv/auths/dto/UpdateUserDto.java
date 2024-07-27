package com.rotciv.auths.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateUserDto {
    @NotEmpty()
    private String email;

    private String province;

    private String district;

    private String fullName;

    private String ward;

    private String addressDetail;

    private String phoneNumber;

    private boolean isBlock;

    private String clerkUserId;
}
