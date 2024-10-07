package com.pineslack.coupons.mapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pineslack.coupons.document.Coupon;
import com.pineslack.coupons.document.Redemption;
import com.pineslack.coupons.dto.*;
import com.pineslack.coupons.util.CouponType;
import com.pineslack.openapi.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

import static com.pineslack.coupons.util.Defaults.DEFAULT_MULTI_USER;
import static com.pineslack.coupons.util.Defaults.DEFAULT_USAGE_LIMIT;

@Service
@RequiredArgsConstructor
public class CouponsMapper {

    private final ObjectMapper objectMapper;

    public CreateCouponRequestDTO toCreateCouponRequestDto(String websiteId, String customerId, Map<String, Object> payload) {
        CreateCouponRequestBody body = objectMapper.convertValue(payload, CreateCouponRequestBody.class);
        Integer usageLimit = body.getUsageLimit();
        Boolean multiUser = body.getMultiUser();

        body.setUsageLimit(usageLimit == null || usageLimit <= 0 ? DEFAULT_USAGE_LIMIT : usageLimit);
        body.setMultiUser(multiUser == null ? DEFAULT_MULTI_USER : multiUser);

        return CreateCouponRequestDTO.builder()
                .requestBody(body)
                .websiteId(websiteId)
                .customerId(customerId)
                .build();
    }

    public RedemptionRequestDTO toRedemptionRequestDto(String websiteId, String customerId, String couponCode, RedemptionRequestBody body) {
        return RedemptionRequestDTO.builder()
                .websiteId(websiteId)
                .customerId(customerId)
                .code(couponCode)
                .requestBody(body)
                .build();
    }

    public CreateCouponResponse toCreateCouponResponse(CreateCouponResponseDTO dto) {
        return new CreateCouponResponse().coupon(toCouponResponse(dto.getCoupon())).status(toStatusResponse(dto.getStatus()));
    }

    public RedemptionResponse toRedemptionResponse(RedemptionResponseDTO dto) {
        return new RedemptionResponse()
                .redemption(toResponseRedemption(dto.getRedemption()))
                .status(toStatusResponse(dto.getStatus()));
    }

    public com.pineslack.openapi.model.Coupon toCouponResponse(Coupon coupon) {
        return objectMapper.convertValue(coupon, com.pineslack.openapi.model.Coupon.class);
    }

    public com.pineslack.openapi.model.Redemption toResponseRedemption(Redemption redemption) {
        return objectMapper.convertValue(redemption, com.pineslack.openapi.model.Redemption.class);
    }

    public Status toStatusResponse(StatusDTO statusDto) {
        return objectMapper.convertValue(statusDto, Status.class);
    }

    public Coupon toCouponDocument(CreateCouponRequestDTO requestDto) {
        Coupon coupon = objectMapper.convertValue(requestDto.getRequestBody(), Coupon.class);
        coupon.setWebsiteId(requestDto.getWebsiteId());
        coupon.setCustomerId(requestDto.getCustomerId());
        return coupon;
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
    public static class CustomResponseRedemptionDeserializer extends JsonDeserializer<com.pineslack.openapi.model.Redemption> {

        @Override
        public com.pineslack.openapi.model.Redemption deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            ObjectMapper objectMapper = customObjectMapper();
            JsonNode root = jsonParser.readValueAsTree();
            CouponType couponType = CouponType.getTypeFromValue(root.get("couponType").asText());

            return switch (couponType) {
                case FIXED_AMOUNT -> objectMapper.treeToValue(root, FixedAmountRedemption.class);
                case PERCENTAGE -> objectMapper.treeToValue(root, PercentageRedemption.class);
                case FREE_PRODUCT -> objectMapper.treeToValue(root, FreeProductRedemption.class);
                case FREE_SHIPPING -> objectMapper.treeToValue(root, FreeShippingRedemption.class);
                default -> null;
            };
        }
    }

    public static class CustomCreateCouponDeserializer extends JsonDeserializer<CreateCouponRequestBody> {

        @Override
        public CreateCouponRequestBody deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
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
