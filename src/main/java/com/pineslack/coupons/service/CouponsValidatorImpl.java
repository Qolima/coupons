package com.pineslack.coupons.service;

import com.pineslack.coupons.dto.CreateCouponRequestDto;
import com.pineslack.coupons.exception.CouponException;
import com.pineslack.coupons.util.CouponType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static io.micrometer.common.util.StringUtils.isBlank;

@Service
@RequiredArgsConstructor
public class CouponsValidatorImpl implements CouponsValidator {

    @Override
    public void validateCreateCoupon(CreateCouponRequestDto request) {

        if (request.getExpireAt().isBefore(request.getValidFrom())) {
            throw new CouponException("Coupon expire date must be after valid from date");
        }

        validateCouponType(request);
    }

    private void validateCouponType(CreateCouponRequestDto request) {

        CouponType type = CouponType.getTypeFromValue(request.getCouponType());
        switch (type) {
            case PERCENTAGE -> {
                BigDecimal value = request.getCouponValue();
                if (value.compareTo(BigDecimal.ONE) < 0 || value.compareTo(BigDecimal.valueOf(100)) > 0) {
                    throw new CouponException("Percentage coupon value must be between 1 and 100");
                }
            }
            case INVALID_TYPE ->
                    throw new CouponException("Coupon type = " + request.getCouponType() + " is not valid");
        }
    }

    private void validateDates(LocalDateTime from, LocalDateTime to) {
        if (from.isAfter(to)) {
            throw new CouponException("Coupon valid from date must be before valid to date");
        }
    }

    private void validateCurrency(String currency) {
        if (isBlank(currency)) {
            throw new CouponException("Currency code is required for this coupon type");
        }
        try {
            Currency.getInstance(currency);
        } catch (IllegalArgumentException e) {
            throw new CouponException("Currency code = " + currency + " is not valid");
        }
    }
}
