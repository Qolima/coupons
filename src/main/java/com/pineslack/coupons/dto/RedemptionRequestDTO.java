package com.pineslack.coupons.dto;

import com.pineslack.openapi.model.RedemptionRequestBody;
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
    private RedemptionRequestBody requestBody;
}
