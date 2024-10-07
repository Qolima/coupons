package com.pineslack.coupons.service;

import com.pineslack.coupons.document.Coupon;
import com.pineslack.coupons.document.Redemption;
import com.pineslack.coupons.dto.*;
import com.pineslack.coupons.exception.NotFoundException;
import com.pineslack.coupons.mapper.CouponsMapper;
import com.pineslack.coupons.persistence.CouponsRepository;
import com.pineslack.coupons.util.CouponCodeGenerator;
import com.pineslack.coupons.util.CouponType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.pineslack.coupons.util.StatusMessages.*;
import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class CouponsServiceImpl implements CouponsService {

    private final CouponsMapper mapper;
    private final CouponsValidator validator;
    private final CouponsRepository repository;

    @Override
    public CreateCouponResponseDTO createCoupon(CreateCouponRequestDTO requestDto) {
        validator.validateCreateCoupon(requestDto);
        Coupon coupon = mapper.toCouponDocument(requestDto);
        coupon.setCode(generateCouponCode(requestDto.getWebsiteId()));
        repository.saveCoupon(coupon);

        return CreateCouponResponseDTO.builder()
                .status(StatusDTO.builder()
                        .code(OK)
                        .message(COUPON_CREATED)
                        .build())
                .coupon(coupon)
                .build();
    }

    @Override
    public RedemptionResponseDTO redeemCoupon(RedemptionRequestDTO requestDto) {
        String websiteId = requestDto.getWebsiteId();
        String customerId = requestDto.getCustomerId();
        String couponCode = requestDto.getCode();

        Coupon coupon = repository.findByWebsiteAndCode(websiteId, couponCode)
                .orElseThrow(() -> new NotFoundException(COUPON_NOT_FOUND.formatted(websiteId, couponCode)));

        validator.validateRedemptionEligibility(requestDto,coupon);

       return doRedemption(coupon, customerId);
    }

    private RedemptionResponseDTO doRedemption(Coupon coupon, String redeemedByCustomerId) {
        Integer usageLimitBalance = coupon.getUsageLimit() - 1;
        if (usageLimitBalance.equals(0)) {
            coupon.setUsageLimit(0);
            coupon.setIsActive(false);
        } else coupon.setUsageLimit(usageLimitBalance);

        Redemption redemption = new Redemption();
        redemption.setCode(coupon.getCode());
        redemption.setWebsiteId(coupon.getWebsiteId());
        redemption.setOwnedByCustomerId(coupon.getCustomerId());
        redemption.setRedeemedByCustomerId(redeemedByCustomerId);
        redemption.setCouponType(coupon.getCouponType());
        redemption.setUsageLimitBalance(usageLimitBalance);

        CouponType type = CouponType.getTypeFromValue(coupon.getCouponType());
        switch (type) {
            case PERCENTAGE -> {
                redemption.setPercentage(coupon.getPercentage());
            }
            case FIXED_AMOUNT -> {
                redemption.setAmount(coupon.getAmount());
            }
            case FREE_PRODUCT -> {
                redemption.setFreeProducts(coupon.getFreeProducts());
            }
            case FREE_SERVICE -> {
                redemption.setFreeServices(coupon.getFreeServices());
            }
        }
        repository.findAndReplaceByWebsiteAndCode(coupon.getWebsiteId(), coupon.getCode(), coupon);
        repository.saveRedemption(redemption);

        return RedemptionResponseDTO.builder()
                .status(StatusDTO.builder()
                        .code(OK)
                        .message(COUPON_REDEEMED)
                        .build())
                .redemption(redemption)
                .build();
    }

    private String generateCouponCode(String websiteId) {
        String code;
        do {
            code = CouponCodeGenerator.generateCouponCode(websiteId);
        } while (repository.existsByWebsiteAndCode(websiteId, code));
        return code;
    }
}
