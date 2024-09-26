package com.pineslack.coupons.service;

import com.pineslack.coupons.dto.CreateCouponRequestDto;
import com.pineslack.coupons.exception.CreateCouponException;
import com.pineslack.coupons.util.CouponType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;

import static io.micrometer.common.util.StringUtils.isBlank;

@Service
@RequiredArgsConstructor
public class CouponsValidatorImpl implements CouponsValidator {

    @Override
    public void validateCreateCoupon(CreateCouponRequestDto request) {

        if (request.getExpireAt().isBefore(request.getValidFrom())) {
            throw new CreateCouponException("Coupon expire date must be after valid from date");
        }

        validateCouponType(request);
    }

    private void validateCouponType(CreateCouponRequestDto request) {

        CouponType type = CouponType.getTypeFromValue(request.getCouponType());
        switch (type) {
            case PERCENTAGE -> {
                BigDecimal value = request.getCouponValue();
                if (value.compareTo(BigDecimal.ONE) < 0 || value.compareTo(BigDecimal.valueOf(100)) > 0) {
                    throw new CreateCouponException("Percentage coupon value must be between 1 and 100");
                }
                validateCurrency(request.getCurrency());
            }
            default -> throw new CreateCouponException("Coupon type = " + request.getCouponType() + " is not valid");
        }
    }

    private void validateCurrency(String currency) {
        if (isBlank(currency)) {
            throw new CreateCouponException("Currency code is required for this coupon type");
        }
        try {
            Currency.getInstance(currency);
        } catch (IllegalArgumentException e) {
            throw new CreateCouponException("Currency code = " + currency + " is not valid");
        }
    }
}
