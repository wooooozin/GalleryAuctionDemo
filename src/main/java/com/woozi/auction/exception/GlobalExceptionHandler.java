package com.woozi.auction.exception;

import com.woozi.auction.common.ResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResultResponse<String> handleEntityNotFound(EntityNotFoundException ex) {
        return ResultResponse.error(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }


}
