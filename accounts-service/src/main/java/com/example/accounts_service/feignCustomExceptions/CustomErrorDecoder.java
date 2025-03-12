package com.example.accounts_service.feignCustomExceptions;

import com.example.accounts_service.exceptions.BadRequestException;
import com.example.accounts_service.exceptions.ConflictException;
import com.example.accounts_service.exceptions.ResourceNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400: return new BadRequestException("Bad request, check information");
            case 404: return new ResourceNotFoundException("Resource not found");
            case 409: return new ConflictException("Resource already exists");
            default: return new Exception("Try again later");
        }

    }

}
