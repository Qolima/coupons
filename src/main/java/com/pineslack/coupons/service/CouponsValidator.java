package com.pineslack.coupons.service;

import com.pineslack.coupons.document.Coupon;
import com.pineslack.coupons.dto.CreateCouponRequestDTO;
import com.pineslack.coupons.dto.RedemptionRequestDTO;

public interface CouponsValidator {

    void validateCreateCoupon(CreateCouponRequestDTO request);

    void validateRedemptionEligibility(RedemptionRequestDTO request, Coupon coupon);
}