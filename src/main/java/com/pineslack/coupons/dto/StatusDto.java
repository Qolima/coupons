package com.pineslack.coupons.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class StatusDto {
    private HttpStatus code;
    private String message;
}
