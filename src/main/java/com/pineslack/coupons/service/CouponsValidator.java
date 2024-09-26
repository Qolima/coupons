package com.pineslack.coupons.service;

import com.pineslack.coupons.document.Coupon;
import com.pineslack.coupons.dto.CreateCouponRequestDto;
import com.pineslack.coupons.dto.CreateCouponResponseDto;

public interface CouponsValidator {

    void validateCreateCoupon(CreateCouponRequestDto request);
}