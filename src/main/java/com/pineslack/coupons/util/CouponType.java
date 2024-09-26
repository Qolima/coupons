package com.pineslack.coupons.util;

import lombok.Getter;

@Getter
public enum CouponType {

    FIXED("fixed"),
    PERCENTAGE("percentage"),
    FREE_PRODUCT("free_product"),
    FREE_SHIPPING("free_shipping"),
    INVALID_TYPE("invalid_type");

    private final String value;

    CouponType(final String value) {
        this.value = value;
    }

    public static CouponType getTypeFromValue(final String value) {
        for (CouponType couponType : CouponType.values()) {
            if (couponType.value.equals(value)) {
                return couponType;
            }
        }
        return INVALID_TYPE;
    }
}
