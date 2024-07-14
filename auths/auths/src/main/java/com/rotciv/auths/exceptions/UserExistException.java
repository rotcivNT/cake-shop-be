package com.rotciv.auths.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
@NoArgsConstructor
public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }
}
