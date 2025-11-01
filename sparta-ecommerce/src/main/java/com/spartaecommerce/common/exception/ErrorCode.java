package com.spartaecommerce.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    ALREADY_PROCESSED_PAYMENT(400, "ALREADY_PROCESSED_PAYMENT", "This is a payment that has already been processed."),
    PROVIDER_ERROR(400, "PROVIDER_ERROR", "This is temporary error. Please try again in a few minutes."),
    EXCEED_MAX_CARD_INSTALLMENT_PLAN(400, "EXCEED_MAX_CARD_INSTALLMENT_PLAN", "Maximum number of installment months exceeded. (installmentPlanMonths)"),
    INVALID_REQUEST(400, "INVALID_REQUEST", "The bad request."),
    NOT_ALLOWED_POINT_USE(400, "NOT_ALLOWED_POINT_USE", "Card point payment failed because the card cannot be used points."),
    INVALID_API_KEY(400, "INVALID_API_KEY", "Incorrect secret key."),
    INVALID_REJECT_CARD(400, "INVALID_REJECT_CARD", "Refer to card issuer/decline."),
    BELOW_MINIMUM_AMOUNT(400, "BELOW_MINIMUM_AMOUNT", "Payment can be made from 100 won or more by credit card, and 200 won or more for account.");

    private final int httpStatus;
    private final String code;
    private final String message;
}
