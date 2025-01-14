package com.pineslack.coupons.persistence;

import com.pineslack.coupons.document.Coupon;
import com.pineslack.coupons.document.Redemption;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@RequiredArgsConstructor
public class CouponsRepositoryImpl implements CouponsRepository {

    // Coupons fields
    public static final String CODE = "code";
    public static final String WEBSITE_ID = "websiteId";
    public static final String CUSTOMER_ID = "customerId";
    public static final String COUPON_VALUE = "couponValue";
    public static final String COUPON_TYPE = "couponType";
    public static final String DESCRIPTION = "description";
    public static final String CURRENCY = "currency";
    public static final String USE_ONCE = "useOnce";
    public static final String IS_ACTIVE = "isActive";
    public static final String PRODUCT_IDS = "productIds";
    public static final String CATEGORY_IDS = "categoryIds";
    public static final String EXPIRE_AT = "expireAt";
    public static final String VALID_FROM = "validFrom";

    // Collections
    public static final String COUPONS_COLLECTION = "coupons";
    public static final String REDEMPTIONS_COLLECTION = "redemptions";

    private final MongoTemplate mongoTemplate;

    @Override
    public void saveCoupon(Coupon coupon) {
        mongoTemplate.insert(coupon, COUPONS_COLLECTION);
    }

    @Override
    public void saveRedemption(Redemption redemption) {
        mongoTemplate.insert(redemption, REDEMPTIONS_COLLECTION);
    }

    @Override
    public boolean existsByWebsiteAndCode(String websiteId, String code) {
        return mongoTemplate.exists(queryByWebsiteIdAndCode(websiteId, code), Coupon.class);
    }

    @Override
    public Optional<Coupon> findByWebsiteAndCustomerIdAndCode(String websiteId, String customerId, String code) {
        return ofNullable(mongoTemplate.findOne(queryByWebsiteIdAndCustomerIdAndCode(websiteId, customerId, code), Coupon.class));
    }

    @Override
    public Optional<Coupon> findByWebsiteAndCode(String websiteId, String code) {
        return ofNullable(mongoTemplate.findOne(queryByWebsiteIdAndCode(websiteId, code), Coupon.class));
    }

    @Override
    public void findAndReplaceByWebsiteAndCode(String websiteId, String code, Coupon coupon) {
        mongoTemplate.findAndReplace(queryByWebsiteIdAndCode(websiteId, code), coupon, COUPONS_COLLECTION);
    }

    private Query queryByWebsiteIdAndCode(String websiteId, String code) {
        return query(where(WEBSITE_ID).is(websiteId).and(CODE).is(code).and(IS_ACTIVE).is(true));
    }

    private Query queryByWebsiteIdAndCustomerIdAndCode(String websiteId, String customerId, String code) {
        return query(where(WEBSITE_ID).is(websiteId).and(CUSTOMER_ID).is(customerId).and(CODE).is(code));
    }
}
