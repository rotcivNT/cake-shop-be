package com.rotciv.auths.mapper;

import com.rotciv.auths.dto.CreateUserDto;
import com.rotciv.auths.dto.UpdateUserDto;
import com.rotciv.auths.entities.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {
    public static CreateUserDto toCreateUserDto(User user) {
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setPassword(user.getPassword());
        createUserDto.setFullName(user.getFullName());
        createUserDto.setProvince(user.getProvince());
        createUserDto.setDistrict(user.getDistrict());
        createUserDto.setEmail(user.getEmail());
        createUserDto.setPhoneNumber(user.getPhoneNumber());
        createUserDto.setBlock(user.isBlock());
        return createUserDto;
    }
    public static User createUserDtoToUser(CreateUserDto createUserDto) {
        User user = new User();
        PasswordEncoder passwordEncoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String hashPassword = passwordEncoder.encode(createUserDto.getPassword());

        user.setPassword(hashPassword);
        user.setFullName(createUserDto.getFullName());
        user.setProvince(createUserDto.getProvince());
        user.setDistrict(createUserDto.getDistrict());
        user.setEmail(createUserDto.getEmail());
        user.setPhoneNumber(createUserDto.getPhoneNumber());
        user.setBlock(createUserDto.isBlock());
        return user;
    }

    public static User updateUserDtoToUser(User user, UpdateUserDto updateUserDto) {
        user.setFullName(updateUserDto.getFullName());
        user.setProvince(updateUserDto.getProvince());
        user.setDistrict(updateUserDto.getDistrict());
        user.setWard(updateUserDto.getWard());
        user.setAddressDetail(updateUserDto.getAddressDetail());
        user.setPhoneNumber(updateUserDto.getPhoneNumber());
        user.setBlock(updateUserDto.isBlock());


        return user;
    }
}
