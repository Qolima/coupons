package com.pineslack.coupons.document;

import com.pineslack.coupons.dto.AmountDTO;
import com.pineslack.coupons.dto.ProductDTO;
import com.pineslack.coupons.dto.ServiceDTO;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@Document("redemptions")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Redemption {
    private String code;
    private String websiteId;
    private String ownedByCustomerId;
    private String redeemedByCustomerId;
    private String couponType;
    private Integer usageLimitBalance;
    private AmountDTO amount; // Fixed Amount Type
    private Integer percentage; // Percentage Type
    private List<ProductDTO> freeProducts; // Free Product Type
    private List<ServiceDTO> freeServices; // Free Service Type
}
