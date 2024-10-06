package com.pineslack.coupons.service;

import com.pineslack.coupons.document.Coupon;
import com.pineslack.coupons.dto.CreateCouponRequestDTO;
import com.pineslack.coupons.dto.RedemptionRequestDTO;
import com.pineslack.coupons.exception.CouponException;
import com.pineslack.openapi.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;

import static com.pineslack.coupons.util.StatusMessages.COUPON_NOT_MULTI_USER;
import static io.micrometer.common.util.StringUtils.isBlank;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class CouponsValidatorImpl implements CouponsValidator {

    @Override
    public void validateCreateCoupon(CreateCouponRequestDTO request) {
        validateDates(toLocalDateTime(request.getRequestBody().getValidFrom()), toLocalDateTime(request.getRequestBody().getExpireAt()));
        validateCouponType(request);
    }

    @Override
    public void validateRedemptionEligibility(RedemptionRequestDTO request, Coupon coupon) {
        if (!coupon.getMultiUser()) {
            if (!coupon.getCustomerId().equals(request.getCustomerId())) {
                throw new CouponException(COUPON_NOT_MULTI_USER.formatted(request.getWebsiteId(), request.getCode()));
            }
        }

        if (!isNullOrEmpty(request.getCartProductIds())) {
            if (!new HashSet<>(request.getCartProductIds()).containsAll(coupon.getEligibleProductIds())) {
                throw new CouponException("Coupon is not eligible for this product(s)");
            }
        }

        if (!isNullOrEmpty(request.getCartCategoryIds())) {
            if (!new HashSet<>(request.getCartCategoryIds()).containsAll(coupon.getEligibleCategoryIds())) {
                throw new CouponException("Coupon is not eligible for this category(s)");
            }
        }

        if (!isNull(request.getCartAmount())) {
            if (isNull(request.getCartAmount().getValue()) && request.getCartAmount().getValue().compareTo(coupon.getEligibleMinAmount().getValue()) > 0) {
                throw new CouponException("Coupon is not eligible for this amount");
            }
        }
    }

    private void validateCouponType(CreateCouponRequestDTO request) {
        CreateCouponRequestBody requestBody = request.getRequestBody();
        switch (requestBody) {
            case CreatePercentageCouponRequest r -> validatePercentage(r.getPercentage());
            case CreateFixedAmountCouponRequest r -> validateAmount(r.getAmount());
            case CreateFreeProductCouponRequest r -> validateFreeProducts(r.getFreeProducts());
            default -> {
            }
        }
    }

    private void validateAmount(CouponAmount amount) {
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

    private void validateFreeProducts(List<FreeProduct> freeProducts) {
        if (isNullOrEmpty(freeProducts)) {
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

    public static boolean isNullOrEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    public static LocalDateTime toLocalDateTime(String date) {
        try {
            return LocalDateTime.parse(date);
        } catch (Exception e) {
            throw new CouponException("Invalid date format: " + date);
        }
    }
}
