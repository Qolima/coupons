package com.pineslack.coupons.mapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pineslack.coupons.document.Coupon;
import com.pineslack.coupons.dto.CouponDto;
import com.pineslack.coupons.dto.CreateCouponRequestDto;
import com.pineslack.coupons.dto.CreateCouponResponseDto;
import com.pineslack.coupons.dto.StatusDto;
import com.pineslack.coupons.util.CouponType;
import com.pineslack.openapi.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CouponsMapper {

    private final ObjectMapper objectMapper;

    public CreateCouponRequestDto toCreateCouponRequestDto(String websiteId, String customerId, Map<String, Object> body) {
        CreateCouponRequestDto requestDto = objectMapper.convertValue(body, CreateCouponRequestDto.class);
        requestDto.setWebsiteId(websiteId);
        requestDto.setCustomerId(customerId);
        return requestDto;
    }

    public CreateCouponResponse toCreateCouponResponse(CreateCouponResponseDto dto) {
        return new CreateCouponResponse().coupon(toCouponResponse(dto.getCoupon())).status(toStatusResponse(dto.getStatus()));
    }

    public com.pineslack.openapi.model.Coupon toCouponResponse(CouponDto couponDto) {
        return objectMapper.convertValue(couponDto, com.pineslack.openapi.model.Coupon.class);
    }

    public Status toStatusResponse(StatusDto statusDto) {
        return objectMapper.convertValue(statusDto, Status.class);
    }

    public Coupon toCouponDocument(CreateCouponRequestDto requestDto) {
        return objectMapper.convertValue(requestDto, Coupon.class);
    }

    public CouponDto toCouponDto(Coupon coupon) {
        return objectMapper.convertValue(coupon, CouponDto.class);
    }

    public static class CustomResponseCuoponDeserializer extends JsonDeserializer<com.pineslack.openapi.model.Coupon> {

        @Override
        public com.pineslack.openapi.model.Coupon deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JsonNode root =jsonParser.readValueAsTree();
            CouponType couponType = CouponType.getTypeFromValue(root.get("couponType").asText());

            switch (couponType) {
                case FIXED_AMOUNT:
                    return objectMapper.treeToValue(root, FixedAmountCoupon.class);
                case PERCENTAGE:
                    return objectMapper.treeToValue(root, PercentageCoupon.class);
                case FREE_PRODUCT:
                    return objectMapper.treeToValue(root, FreeProductCoupon.class);
                case FREE_SHIPPING:
                    return objectMapper.treeToValue(root, FreeShippingCoupon.class);
                default: return null;
            }
        }
    }

}
