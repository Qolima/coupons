package com.pineslack.coupons.dto;

import com.pineslack.openapi.model.CreateCouponRequestBody;
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
public class CreateCouponRequestDTO {
    private String websiteId;
    private String customerId;
    private CreateCouponRequestBody requestBody;
}
