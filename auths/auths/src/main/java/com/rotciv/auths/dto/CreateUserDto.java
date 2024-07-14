package com.rotciv.auths.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CreateUserDto {
    private String email;
    private String password;
    private String fullName;
    private String province;
    private String district;

    private String phoneNumber;
    private boolean isBlock;
}
