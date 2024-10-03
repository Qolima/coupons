package com.pineslack.coupons.persistence;

import com.pineslack.coupons.document.Coupon;

public interface CouponsRepository {

    public Coupon saveCoupon(Coupon coupon);

    public boolean existsByWebsiteAndCode(String websiteId, String code);
}
