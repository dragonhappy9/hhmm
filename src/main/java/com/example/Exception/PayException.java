package com.example.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "failed payment")
public class PayException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public PayException(String message) {
        super(message);
    }
}
