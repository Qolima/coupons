package com.pineslack.coupons.document;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Document("coupons")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Coupon {
    private String code;
    private String customerId;
    private String couponValue;
    private String couponType;
    private String description;
    private String currency;
    private Boolean useOnce;
    private Boolean isActive;
    private List<String> productIds;
    private List<String> categoryIds;
    private LocalDateTime expireAt;
    private LocalDateTime validFrom;
}
