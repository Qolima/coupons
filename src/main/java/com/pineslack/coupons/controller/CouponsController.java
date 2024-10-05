package com.pineslack.coupons.controller;

import com.pineslack.coupons.dto.CreateCouponRequestDTO;
import com.pineslack.coupons.dto.CreateCouponResponseDTO;
import com.pineslack.coupons.dto.RedemptionRequestDTO;
import com.pineslack.coupons.dto.RedemptionResponseDTO;
import com.pineslack.coupons.mapper.CouponsMapper;
import com.pineslack.coupons.service.CouponsService;
import com.pineslack.openapi.model.CreateCouponResponse;
import com.pineslack.openapi.model.RedemptionRequestBody;
import com.pineslack.openapi.model.RedemptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/coupons/v1")
@RequiredArgsConstructor
public class CouponsController {

    private final CouponsService service;
    private final CouponsMapper mapper;

    public static final String CREATE_COUPON_URL = "/websites/{websiteId}/customers/{customerId}";
    public static final String COUPON_REDEMPTION_URL = "/websites/{websiteId}/customers/{customerId}/coupons/{code}/redemptions";

    @PostMapping(value = CREATE_COUPON_URL)
    public ResponseEntity<CreateCouponResponse> createCoupon(
            final @PathVariable String websiteId,
            final @PathVariable String customerId,
            final @RequestBody Map<String, Object> payload) {

        CreateCouponRequestDTO requestDto = mapper.toCreateCouponRequestDto(websiteId, customerId, payload);
        CreateCouponResponseDTO responseDto = service.createCoupon(requestDto);

        return ResponseEntity.ok(mapper.toCreateCouponResponse(responseDto));
    }

    @PostMapping(value = COUPON_REDEMPTION_URL)
    public ResponseEntity<RedemptionResponse> redeemCoupon(
            final @PathVariable String websiteId,
            final @PathVariable String customerId,
            final @PathVariable String code,
            final @RequestBody RedemptionRequestBody body) {

        RedemptionRequestDTO requestDto = mapper.toRedemptionRequestDto(websiteId, customerId, code, body);
        RedemptionResponseDTO responseDto = service.redeemCoupon(requestDto);

        return ResponseEntity.ok(mapper.toRedemptionResponse(responseDto));
    }
}
