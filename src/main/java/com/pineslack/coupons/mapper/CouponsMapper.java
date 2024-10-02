package com.pineslack.coupons.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pineslack.coupons.document.Coupon;
import com.pineslack.coupons.dto.CouponDto;
import com.pineslack.coupons.dto.CreateCouponRequestDto;
import com.pineslack.coupons.dto.CreateCouponResponseDto;
import com.pineslack.coupons.dto.StatusDto;
import com.pineslack.openapi.model.CreateCouponResponse;
import com.pineslack.openapi.model.Status;
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
        return new CreateCouponResponse().coupon(toCoupon(dto.getCoupon())).status(toStatus(dto.getStatus()));
    }

    public com.pineslack.openapi.model.Coupon toCoupon(CouponDto couponDto) {
        return new com.pineslack.openapi.model.Coupon().couponType(couponDto.getCouponType())
                .customerId(couponDto.getCustomerId())
                .code(couponDto.getCode())
                .description(couponDto.getDescription())
             //   .expireAt(couponDto.getExpireAt())
                .productIds(couponDto.getProductIds())
                .categoryIds(couponDto.getCategoryIds())
                .useOnce(couponDto.getUseOnce());
              //  .validFrom(couponDto.getValidFrom());
    }

    public Status toStatus(StatusDto statusDto){
        return objectMapper.convertValue(statusDto, Status.class);
    }

    public Coupon toCoupon(CreateCouponRequestDto requestDto) {
        return Coupon.builder()
                .couponType(requestDto.getCouponType())
                .customerId(requestDto.getCustomerId())
                .description(requestDto.getDescription())
               // .expireAt(requestDto.getExpireAt())
                .productIds(requestDto.getProductIds())
                .categoryIds(requestDto.getCategoryIds())
                .useOnce(requestDto.getUseOnce())
                .build();
    }

    public CouponDto toCouponDto(Coupon coupon) {
        return objectMapper.convertValue(coupon, CouponDto.class);
    }
}
