package com.pineslack.coupons.persistence;

import com.pineslack.coupons.document.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

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

    private final MongoTemplate mongoTemplate;

    @Override
    public Coupon saveCoupon(Coupon coupon) {
        return mongoTemplate.insert(coupon, COUPONS_COLLECTION);
    }

    @Override
    public boolean existsByWebsiteAndCode(String websiteId, String code) {
        return mongoTemplate.exists(queryByWebsiteIdAndCode(websiteId, code), Coupon.class);
    }

    private Query queryByWebsiteIdAndCode(String websiteId, String code) {
        return query(where(WEBSITE_ID).is(websiteId).and(CODE).is(code));
    }
}
