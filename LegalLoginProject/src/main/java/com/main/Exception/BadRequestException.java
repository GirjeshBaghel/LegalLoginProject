package com.main.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class BadRequestException  extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
    }


}
