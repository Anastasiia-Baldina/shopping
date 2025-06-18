package ru.vse.shop.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vse.shop.dto.ErrorResponse;


@RestControllerAdvice
public class ErrorHandlerController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class, produces = "application/json")
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ErrorResponse()
                .setStatus(HttpStatus.BAD_REQUEST)
                .setMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class, produces = "application/json")
    public ErrorResponse handleException(Exception e) {
        return new ErrorResponse()
                .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .setMessage(e.getMessage());
    }
}
