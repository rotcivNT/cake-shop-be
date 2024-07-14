package com.rotciv.auths.controllers;

import com.rotciv.auths.dto.CreateUserDto;
import com.rotciv.auths.dto.ResponseDto;
import com.rotciv.auths.dto.UpdateUserDto;
import com.rotciv.auths.entities.User;
import com.rotciv.auths.exceptions.UserExistException;
import com.rotciv.auths.services.AuthService;
import com.rotciv.auths.services.impl.AuthServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/api/auth", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createUser(@RequestBody CreateUserDto createUserDto) {
        this.authService.createUser(createUserDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body((new ResponseDto("201", "User was created successfully")));
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        User user = this.authService.getUserByEmail(email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody UpdateUserDto updateUserDto) {
        User user = this.authService.updateUserByEmail(updateUserDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }
}
