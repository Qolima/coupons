package com.pineslack.coupons.dto;

import com.pineslack.coupons.document.Coupon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCouponResponseDTO extends ResponseDTO {
    private Coupon coupon;
}
