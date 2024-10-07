package com.pineslack.coupons.persistence;

import com.pineslack.coupons.document.Coupon;
import com.pineslack.coupons.document.Redemption;

import java.util.Optional;

public interface CouponsRepository {

    public void saveCoupon(Coupon coupon);

    public void saveRedemption(Redemption redemption);

    public boolean existsByWebsiteAndCode(String websiteId, String code);

    public Optional<Coupon> findByWebsiteAndCustomerIdAndCode(String websiteId, String customerId, String code);

    public Optional<Coupon> findByWebsiteAndCode(String websiteId, String code);

    public void findAndReplaceByWebsiteAndCode(String websiteId, String code, Coupon coupon);
}
