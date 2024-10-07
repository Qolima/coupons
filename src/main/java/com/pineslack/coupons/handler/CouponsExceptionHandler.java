package com.pineslack.coupons.handler;

import com.pineslack.coupons.dto.ResponseDTO;
import com.pineslack.coupons.dto.StatusDTO;
import com.pineslack.coupons.exception.CouponException;
import com.pineslack.coupons.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CouponsExceptionHandler {

    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Something went wrong internally.";

    @ExceptionHandler({CouponException.class})
    public ResponseEntity<ResponseDTO> handleBadRequest(RuntimeException e, WebRequest request) {
        return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ResponseDTO> handleNotFound(RuntimeException e, WebRequest request) {
        return createResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

/*
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseDto> handleServerError(RuntimeException e, WebRequest request) {
        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MESSAGE);
    }
*/

    private ResponseEntity<ResponseDTO> createResponse(HttpStatus status, String message) {
        ResponseDTO response = ResponseDTO.builder()
                .status(StatusDTO.builder()
                        .code(status)
                        .message(message)
                        .build())
                .build();
        return new ResponseEntity<>(response, status);
    }
}
