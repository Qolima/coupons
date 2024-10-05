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
public class CreateCouponRequestDTO {
    private String websiteId;
    private String customerId;
    private String couponType;
    private String description;
    private Boolean multiUser=false;
    private Integer usageLimit = DEFAULT_USAGE_LIMIT;
    private List<String> eligibleProductIds;
    private List<String> eligibleCategoryIds;
    private AmountDTO eligibleMinAmount;
    private LocalDateTime expireAt;
    private LocalDateTime validFrom;
    private AmountDTO amount; // Fixed Amount Type
    private Integer percentage; // Percentage Type
    private List<ProductDTO> freeProducts; // Free Product Type
}
