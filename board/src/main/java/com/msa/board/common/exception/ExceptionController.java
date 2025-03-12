package com.msa.board.common.exception;


import com.msa.board.common.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {

        ErrorResponse response =  ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다")
                .build();
        for(FieldError fieldError : e.getFieldErrors()){
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseBody
    public ErrorResponse invalidCommunityHandler(NoSuchElementException e) {

        ErrorResponse response =  ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다, " + e.getMessage())
                .build();
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BoardException.class)
    @ResponseBody
    public ErrorResponse invalidCommunityHandler(BoardException e) {

        ErrorResponse response =  ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다, " + e.getMessage())
                .build();
        return response;
    }
}
