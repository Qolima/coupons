package com.pineslack.coupons.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCouponRequestDto {
    private String websiteId;
    private String customerId;
    private BigDecimal couponValue;
    private String couponType;
    private String description;
    private String currency;
    private Boolean useOnce;
    private List<String> productIds;
    private List<String> categoryIds;
    private LocalDateTime expireAt;
    private LocalDateTime validFrom;
}
