package com.pineslack.coupons.service;

import com.pineslack.coupons.dto.CreateCouponRequestDto;

public interface CouponsValidator {

    void validateCreateCoupon(CreateCouponRequestDto request);
}