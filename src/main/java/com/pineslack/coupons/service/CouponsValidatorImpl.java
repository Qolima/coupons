package com.pineslack.coupons.service;

import com.pineslack.coupons.dto.AmountDto;
import com.pineslack.coupons.dto.CreateCouponRequestDto;
import com.pineslack.coupons.dto.FreeProductDto;
import com.pineslack.coupons.exception.CouponException;
import com.pineslack.coupons.util.CouponType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

import static io.micrometer.common.util.StringUtils.isBlank;

@Service
@RequiredArgsConstructor
public class CouponsValidatorImpl implements CouponsValidator {

    @Override
    public void validateCreateCoupon(CreateCouponRequestDto request) {
        validateDates(request.getValidFrom(), request.getExpireAt());
        validateCouponType(request);
    }

    private void validateCouponType(CreateCouponRequestDto request) {

        CouponType type = CouponType.getTypeFromValue(request.getCouponType());
        switch (type) {
            case PERCENTAGE -> {
                Integer value = request.getPercentage();
                validatePercentage(value);
            }
            case FIXED_AMOUNT -> {
                AmountDto amount = request.getAmount();
                validateAmount(amount);
            }
            case FREE_PRODUCT -> {
                List<FreeProductDto> freeProducts = request.getFreeProducts();
                validateFreeProducts(freeProducts);
            }
            case INVALID_TYPE ->
                    throw new CouponException("Coupon type = " + request.getCouponType() + " is not valid");
        }
    }

    private void validateAmount(AmountDto amount){
        if (amount == null) {
            throw new CouponException("Coupon amount is required");
        }
        if (amount.getValue() == null || amount.getValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new CouponException("Coupon amount value is required and must be greater than 0");
        }
        validateCurrency(amount.getCurrency());
    }

    private void validatePercentage(Integer percentage) {
        if (percentage == null) {
            throw new CouponException("Coupon percentage is required");
        }
        if (percentage.compareTo(1) < 0 || percentage.compareTo(100) > 0) {
            throw new CouponException("Coupon percentage value must be between 1 and 100");
        }
    }

    private void validateFreeProducts(List<FreeProductDto> freeProducts) {
        if (freeProducts == null || freeProducts.isEmpty()) {
            throw new CouponException("Free products are required for this coupon type");
        }
    }

    private void validateDates(LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null) {
            throw new CouponException("Coupon validFrom and expireAt dates are required");
        }
        if (from.isAfter(to)) {
            throw new CouponException("Coupon valid from date must be before expiry date");
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
