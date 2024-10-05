package com.pineslack.coupons.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    StatusDTO status;
}
