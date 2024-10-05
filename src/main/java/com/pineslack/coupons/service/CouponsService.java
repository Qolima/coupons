package com.pineslack.coupons.service;

import com.pineslack.coupons.dto.CreateCouponRequestDTO;
import com.pineslack.coupons.dto.CreateCouponResponseDTO;
import com.pineslack.coupons.dto.RedemptionRequestDTO;
import com.pineslack.coupons.dto.RedemptionResponseDTO;

public interface CouponsService {

    CreateCouponResponseDTO createCoupon(CreateCouponRequestDTO requestDto);

    RedemptionResponseDTO redeemCoupon(RedemptionRequestDTO requestDto);
}
