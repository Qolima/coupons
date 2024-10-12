package com.pineslack.coupons.dto;


import com.pineslack.openapi.model.RedemptionRequestBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RedemptionRequestDTO {
    private String code;
    private String websiteId;
    private String customerId;
    private RedemptionRequestBody requestBody;
}
