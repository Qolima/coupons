package com.pineslack.coupons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private AmountDTO amount;
    private List<String> categoryIds;
    private List<String> productIds;
    private List<ProductDTO> products;
}
