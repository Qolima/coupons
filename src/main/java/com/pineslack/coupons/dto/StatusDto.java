package com.pineslack.coupons.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusDto {
    private HttpStatus code;
    private String message;
}
