package com.pineslack.coupons.util;

import java.util.UUID;

public class CouponCodeGenerator {

    public static String generateCouponCode(String websiteId) {
        String randomString = UUID.randomUUID().toString().substring(0, 8);

        // TODO: Hashing
        String hashedCode = randomString + websiteId;
        return hashedCode.toUpperCase();
    }
}
