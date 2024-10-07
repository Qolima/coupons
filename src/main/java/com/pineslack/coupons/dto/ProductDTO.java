package com.pineslack.coupons.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String productId;
    private Integer quantity;
}
