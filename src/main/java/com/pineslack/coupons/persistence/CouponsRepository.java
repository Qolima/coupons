package com.pineslack.coupons.persistence;

import com.pineslack.coupons.document.Coupon;
import com.pineslack.coupons.document.Redemption;

import java.util.Optional;

public interface CouponsRepository {

    public Coupon saveCoupon(Coupon coupon);

    public Redemption saveRedemption(Redemption redemption);

    public boolean existsByWebsiteAndCode(String websiteId, String code);

    public Optional<Coupon> findByWebsiteAndCustomerIdAndCode(String websiteId, String customerId, String code);

    public Optional<Coupon> findByWebsiteAndCode(String websiteId, String code);
}
