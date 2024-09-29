package com.pineslack.coupons.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pineslack.coupons.document.Coupon;
import com.pineslack.coupons.dto.CouponDto;
import com.pineslack.coupons.dto.CreateCouponRequestDto;
import com.pineslack.coupons.dto.CreateCouponResponseDto;
import com.pineslack.openapi.model.CreateCouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CouponsMapper {

    private final ObjectMapper objectMapper;

    public CreateCouponRequestDto toCreateCouponRequestDto(String websiteId, String customerId, Map<String, Object> body) {
        CreateCouponRequestDto requestDto = objectMapper.convertValue(body, CreateCouponRequestDto.class);
        requestDto.setWebsiteId(websiteId);
        requestDto.setCustomerId(customerId);
        return requestDto;
    }

    public CreateCouponResponse toCreateCouponResponse(CreateCouponResponseDto dto) {
        return objectMapper.convertValue(dto, CreateCouponResponse.class);
    }

    public Coupon toCoupon(CreateCouponRequestDto requestDto) {
        return objectMapper.convertValue(requestDto, Coupon.class);
    }

    public CouponDto toCouponDto(Coupon coupon) {
        return objectMapper.convertValue(coupon, CouponDto.class);
    }
}
