package com.pineslack.coupons.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pineslack.coupons.dto.AmountDto;
import com.pineslack.coupons.dto.FreeProductDto;
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
    private String websiteId;
    private String customerId;
    private String couponType;
    private String description;
    private Integer usageLimit;
    private Boolean isActive;
    private List<String> productIds;
    private List<String> categoryIds;
    private LocalDateTime expireAt;
    private LocalDateTime validFrom;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AmountDto amount; // Fixed Amount Type
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer percentage; // Percentage Type
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FreeProductDto> freeProducts; // Free Product Type
}
