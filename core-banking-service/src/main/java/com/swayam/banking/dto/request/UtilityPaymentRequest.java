package com.swayam.banking.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UtilityPaymentRequest {

    private Long providerId;
    private BigDecimal amount;
    private String referenceNumber;
    private String account;

}