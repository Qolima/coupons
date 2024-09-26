package com.pineslack.coupons.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCouponResponseDto extends ResponseDto {
   private CouponDto coupon;
}
