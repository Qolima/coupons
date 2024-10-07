package com.pineslack.coupons.dto;

import com.pineslack.openapi.model.CreateCouponRequestBody;
import lombok.*;


@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCouponRequestDTO {
    private String websiteId;
    private String customerId;
    private CreateCouponRequestBody requestBody;
}
