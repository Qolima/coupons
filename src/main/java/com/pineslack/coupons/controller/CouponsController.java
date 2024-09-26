package com.pineslack.coupons.controller;

import com.pineslack.coupons.dto.CreateCouponRequestDto;
import com.pineslack.coupons.dto.CreateCouponResponseDto;
import com.pineslack.coupons.mapper.CouponsMapper;
import com.pineslack.coupons.service.CouponsService;
import com.pineslack.openapi.model.CreateCouponRequest;
import com.pineslack.openapi.model.CreateCouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/coupons/v1")
@RequiredArgsConstructor
public class CouponsController {

    private final CouponsService service;
    private final CouponsMapper mapper;

    public static final String CREATE_COUPON_URL = "/websites/{websiteId}/customers/{customerId}";

    @PostMapping(value = CREATE_COUPON_URL)
    public ResponseEntity<CreateCouponResponse> createCoupon(
            final @PathVariable String websiteId,
            final @PathVariable String customerId,
            final @RequestBody CreateCouponRequest body) {

        CreateCouponRequestDto requestDto = mapper.toCreateCouponRequestDto(websiteId, customerId, body);
        CreateCouponResponseDto responseDto = service.createCoupon(requestDto);

        CreateCouponResponse response = mapper.toCreateCouponResponse(responseDto);
        return ResponseEntity.ok(response);
    }
}
