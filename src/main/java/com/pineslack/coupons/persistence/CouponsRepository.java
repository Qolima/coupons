package com.pineslack.coupons.persistence;

import com.pineslack.coupons.document.Coupon;
import com.pineslack.coupons.document.Redemption;

import java.util.Optional;

public interface CouponsRepository {

    void saveCoupon(Coupon coupon);

    void saveRedemption(Redemption redemption);

    boolean existsByWebsiteAndCode(String websiteId, String code);

    Optional<Coupon> findByWebsiteAndCustomerIdAndCode(String websiteId, String customerId, String code);

    Optional<Coupon> findByWebsiteAndCode(String websiteId, String code);

    void findAndReplaceByWebsiteAndCode(String websiteId, String code, Coupon coupon);
}
