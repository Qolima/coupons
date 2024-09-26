package com.pineslack.coupons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CouponDto {
    private String websiteId;
    private String customerId;
    private BigDecimal couponValue;
    private String couponType;
    private String description;
    private String currency;
    private Boolean useOnce;
    private Boolean isActive;
    private List<String> productIds;
    private List<String> categoryIds;
    private LocalDateTime expireAt;
    private LocalDateTime validFrom;
}
