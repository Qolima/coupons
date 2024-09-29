package com.pineslack.coupons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private String code;
    private String websiteId;
    private String customerId;
    private String couponType;
    private String description;
    private Boolean useOnce;
    private List<String> productIds;
    private List<String> categoryIds;
    private LocalDateTime expireAt;
    private LocalDateTime validFrom;
    private AmountDto amount; // Fixed Amount Type
    private Integer percentage; // Percentage Type
    private List<FreeProductDto> freeProducts; // Free Product Type
}
