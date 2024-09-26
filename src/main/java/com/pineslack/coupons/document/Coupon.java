package com.pineslack.coupons.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
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
    private String websiteId;
    private String customerId;
    private BigDecimal couponValue;
    private String couponType;
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currency;
    private Boolean useOnce;
    private Boolean isActive;
    private List<String> productIds;
    private List<String> categoryIds;
    private LocalDateTime expireAt;
    private LocalDateTime validFrom;
}
