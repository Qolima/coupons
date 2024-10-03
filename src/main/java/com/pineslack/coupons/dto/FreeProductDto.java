package com.pineslack.coupons.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreeProductDto {
    private String productId;
    private Integer quantity;
}
