package com.pineslack.coupons.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pineslack.coupons.document.Coupon;
import com.pineslack.coupons.dto.CouponDto;
import com.pineslack.coupons.dto.CreateCouponRequestDto;
import com.pineslack.coupons.dto.CreateCouponResponseDto;
import com.pineslack.openapi.model.CreateCouponRequest;
import com.pineslack.openapi.model.CreateCouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class CouponsMapper {

    private final ObjectMapper objectMapper;

    public CreateCouponRequestDto toCreateCouponRequestDto(String websiteId, String customerId, CreateCouponRequest body) {
        return CreateCouponRequestDto.builder()
                .websiteId(websiteId)
                .customerId(customerId)
                .couponValue(body.getCouponValue())
                .couponType(body.getCouponType())
                .description(body.getDescription())
                .currency(body.getCurrency())
                .useOnce(body.getUseOnce())
                .productIds(body.getProductIds())
                .categoryIds(body.getCategoryIds())
                .expireAt(toLocalDateTime(body.getExpireAt()))
                .validFrom(toLocalDateTime(body.getValidFrom()))
                .build();
    }

    private LocalDateTime toLocalDateTime(OffsetDateTime offsetDateTime) {
        return offsetDateTime.toLocalDateTime();
    }

    public CreateCouponResponse toCreateCouponResponse(CreateCouponResponseDto dto) {
        return objectMapper.convertValue(dto, CreateCouponResponse.class);
    }

    public Coupon toCoupon(CreateCouponRequestDto requestDto) {
        return Coupon.builder()
                .websiteId(requestDto.getWebsiteId())
                .customerId(requestDto.getCustomerId())
                .couponValue(requestDto.getCouponValue())
                .couponType(requestDto.getCouponType())
                .description(requestDto.getDescription())
                .currency(requestDto.getCurrency())
                .useOnce(requestDto.getUseOnce())
                .productIds(requestDto.getProductIds())
                .categoryIds(requestDto.getCategoryIds())
                .expireAt(requestDto.getExpireAt())
                .validFrom(requestDto.getValidFrom())
                .build();
    }

    public CouponDto toCouponDto(Coupon coupon) {
        return objectMapper.convertValue(coupon, CouponDto.class);
    }
}
