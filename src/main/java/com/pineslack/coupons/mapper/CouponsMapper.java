package com.pineslack.coupons.mapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pineslack.coupons.document.Coupon;
import com.pineslack.coupons.dto.*;
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

    public CreateCouponRequestDTO toCreateCouponRequestDto(String websiteId, String customerId, Map<String, Object> payload) {
        CreateCouponBase body = objectMapper.convertValue(payload, CreateCouponBase.class);

        CreateCouponRequestDTO requestDto = objectMapper.convertValue(body, CreateCouponRequestDTO.class);
        requestDto.setWebsiteId(websiteId);
        requestDto.setCustomerId(customerId);
        return requestDto;
    }

    public RedemptionRequestDTO toRedemptionRequestDto(String websiteId, String customerId, String couponCode, RedemptionRequestBody body) {
        RedemptionRequestDTO requestDto = objectMapper.convertValue(body, RedemptionRequestDTO.class);
        requestDto.setWebsiteId(websiteId);
        requestDto.setCustomerId(customerId);
        requestDto.setCode(couponCode);
        return requestDto;
    }

    public CreateCouponResponse toCreateCouponResponse(CreateCouponResponseDTO dto) {
        return new CreateCouponResponse().coupon(toCouponResponse(dto.getCoupon())).status(toStatusResponse(dto.getStatus()));
    }

    public RedemptionResponse toRedemptionResponse(RedemptionResponseDTO dto) {
        return new RedemptionResponse()
                .status(toStatusResponse(dto.getStatus()));
    }

    public com.pineslack.openapi.model.Coupon toCouponResponse(Coupon coupon) {
        return objectMapper.convertValue(coupon, com.pineslack.openapi.model.Coupon.class);
    }

    public Status toStatusResponse(StatusDTO statusDto) {
        return objectMapper.convertValue(statusDto, Status.class);
    }

    public Coupon toCouponDocument(CreateCouponRequestDTO requestDto) {
        return objectMapper.convertValue(requestDto, Coupon.class);
    }

    public static class CustomResponseCuoponDeserializer extends JsonDeserializer<com.pineslack.openapi.model.Coupon> {

        @Override
        public com.pineslack.openapi.model.Coupon deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            ObjectMapper objectMapper = customObjectMapper();
            JsonNode root = jsonParser.readValueAsTree();
            CouponType couponType = CouponType.getTypeFromValue(root.get("couponType").asText());

            return switch (couponType) {
                case FIXED_AMOUNT -> objectMapper.treeToValue(root, FixedAmountCoupon.class);
                case PERCENTAGE -> objectMapper.treeToValue(root, PercentageCoupon.class);
                case FREE_PRODUCT -> objectMapper.treeToValue(root, FreeProductCoupon.class);
                case FREE_SHIPPING -> objectMapper.treeToValue(root, FreeShippingCoupon.class);
                default -> null;
            };
        }
    }

    public static class CustomCreateCouponDeserializer extends JsonDeserializer<CreateCouponBase> {

        @Override
        public CreateCouponBase deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            ObjectMapper objectMapper = customObjectMapper();
            JsonNode root = jsonParser.readValueAsTree();
            CouponType couponType = CouponType.getTypeFromValue(root.get("couponType").asText());

            return switch (couponType) {
                case FIXED_AMOUNT -> objectMapper.treeToValue(root, CreateFixedAmountCouponRequest.class);
                case PERCENTAGE -> objectMapper.treeToValue(root, CreatePercentageCouponRequest.class);
                case FREE_PRODUCT -> objectMapper.treeToValue(root, CreateFreeProductCouponRequest.class);
                case FREE_SHIPPING -> objectMapper.treeToValue(root, CreateFreeShippingCouponRequest.class);
                default -> null;
            };
        }
    }

    private static ObjectMapper customObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

}
