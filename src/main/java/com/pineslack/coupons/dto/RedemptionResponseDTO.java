package com.pineslack.coupons.dto;

import com.pineslack.coupons.document.Redemption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
public class RedemptionResponseDTO extends ResponseDTO {

    private Redemption redemption;
}
