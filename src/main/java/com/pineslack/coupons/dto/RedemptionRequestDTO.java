package com.pineslack.coupons.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RedemptionRequestDTO {
    private String code;
    private String websiteId;
    private String customerId;
    private List<String> cartProductIds;
    private List<String> cartCategoryIds;
    private AmountDTO cartAmount;
}
