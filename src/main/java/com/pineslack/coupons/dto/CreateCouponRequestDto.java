package com.pineslack.coupons.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.pineslack.coupons.util.Defaults.DEFAULT_USAGE_LIMIT;


@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCouponRequestDto {
    private String websiteId;
    private String customerId;
    private String couponType;
    private String description;
    private Integer usageLimit = DEFAULT_USAGE_LIMIT;
    private List<String> productIds;
    private List<String> categoryIds;
    private LocalDateTime expireAt;
    private LocalDateTime validFrom;
    private AmountDto amount; // Fixed Amount Type
    private Integer percentage; // Percentage Type
    private List<FreeProductDto> freeProducts; // Free Product Type
}
