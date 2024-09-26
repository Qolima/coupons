package com.pineslack.coupons.service;

import com.pineslack.coupons.document.Coupon;
import com.pineslack.coupons.dto.CreateCouponRequestDto;
import com.pineslack.coupons.dto.CreateCouponResponseDto;
import com.pineslack.coupons.dto.StatusDto;
import com.pineslack.coupons.mapper.CouponsMapper;
import com.pineslack.coupons.persistence.CouponsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class CouponsServiceImpl implements CouponsService {

    private final CouponsMapper mapper;
    private final CouponsValidator validator;
    private final CouponsRepository repository;

    @Override
    public CreateCouponResponseDto createCoupon(CreateCouponRequestDto requestDto) {
        validator.validateCreateCoupon(requestDto);
        Coupon coupon = mapper.toCoupon(requestDto);
        repository.saveCoupon(coupon);

        return CreateCouponResponseDto.builder()
                .status(StatusDto.builder()
                        .code(OK)
                        .message("Coupon created successfully")
                        .build())
                .build();
    }
}
