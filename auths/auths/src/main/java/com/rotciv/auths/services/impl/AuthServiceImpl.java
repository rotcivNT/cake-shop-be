package com.rotciv.auths.services.impl;

import com.rotciv.auths.dto.CreateUserDto;
import com.rotciv.auths.dto.UpdateUserDto;
import com.rotciv.auths.entities.User;
import com.rotciv.auths.exceptions.UserExistException;
import com.rotciv.auths.mapper.UserMapper;
import com.rotciv.auths.repository.AuthRepository;
import com.rotciv.auths.services.AuthService;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@DynamicUpdate
public class AuthServiceImpl implements AuthService {
    private AuthRepository authRepository;
    @Override
    public void createUser(CreateUserDto createUserDto) throws UserExistException {
        User createUser = UserMapper.createUserDtoToUser(createUserDto);
        User user = authRepository.findByEmail(createUser.getEmail());
        createUser.setCreatedAt(LocalDateTime.now());
        createUser.setUpdatedAt(LocalDateTime.now());
        if (user != null) {
            throw new UserExistException("User already exist");
        }
        System.out.println(createUser);
        authRepository.save(createUser);

    }

    @Override
    public User getUserByEmail(String email) {
        User user = authRepository.findByEmail(email);
        if (user == null) {
            user  = authRepository.findByClerkUserId(email);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
        }
        return user;
    }

    @Override
    public User updateUserByEmail(UpdateUserDto updateUserDto) {
        User user = authRepository.findByEmail(updateUserDto.getEmail());
        if (user == null) {
            user = new User();
            user.setEmail(updateUserDto.getEmail());
            user.setCreatedAt(LocalDateTime.now());
        }
        User updatedUser = UserMapper.updateUserDtoToUser(user, updateUserDto);
        user.setUpdatedAt(LocalDateTime.now());
        authRepository.save(updatedUser);
        return updatedUser;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = authRepository.findAll();
        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found");
        }
        return users;
    }


}
