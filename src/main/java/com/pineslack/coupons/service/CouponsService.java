package com.pineslack.coupons.service;

import com.pineslack.coupons.dto.CreateCouponRequestDto;
import com.pineslack.coupons.dto.CreateCouponResponseDto;

public interface CouponsService {

    CreateCouponResponseDto createCoupon(CreateCouponRequestDto requestDto);
}
