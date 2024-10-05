package com.pineslack.coupons.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AmountDTO {
    private String currency;
    private BigDecimal value;
}
