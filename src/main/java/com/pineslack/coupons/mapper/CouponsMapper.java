package com.pineslack.coupons.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pineslack.coupons.document.Coupon;
import com.pineslack.coupons.dto.CreateCouponRequestDto;
import com.pineslack.openapi.model.CreateCouponRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
               // .expireAt(body.getExpireAt())
              //  .validFrom(body.getValidFrom())
                .build();
    }

    public Coupon toCoupon(CreateCouponRequestDto requestDto) {
        return Coupon.builder()
                .code(requestDto.getCode())
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
}
