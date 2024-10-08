package com.pineslack.coupons.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pineslack.coupons.dto.AmountDTO;
import com.pineslack.coupons.dto.EligibleDTO;
import com.pineslack.coupons.dto.ProductDTO;
import com.pineslack.coupons.dto.ServiceDTO;
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
    private Boolean isActive=true;
    private Boolean multiUser;
    private EligibleDTO eligible;
    private LocalDateTime expireAt;
    private LocalDateTime validFrom;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AmountDTO amount; // Fixed Amount Type
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer percentage; // Percentage Type
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ProductDTO> freeProducts; // Free Product Type
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ServiceDTO> freeServices; // Free Service Type
}
