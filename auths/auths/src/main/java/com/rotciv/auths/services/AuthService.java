package com.rotciv.auths.services;

import com.rotciv.auths.dto.CreateUserDto;
import com.rotciv.auths.dto.UpdateUserDto;
import com.rotciv.auths.entities.User;
import com.rotciv.auths.exceptions.UserExistException;

import java.util.List;
import java.util.Set;

public interface AuthService {
    void createUser(CreateUserDto createUserDto) throws UserExistException;
    User getUserByEmail(String email);
    User updateUserByEmail(UpdateUserDto updateUserDto);
    List<User> getAllUsers();
}
