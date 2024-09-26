package com.pineslack.coupons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Builder
public class CreateCouponRequestDto {
    private String code;
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
