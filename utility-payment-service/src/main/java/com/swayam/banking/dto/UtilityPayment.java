package com.swayam.banking.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UtilityPayment {
    private Long providerId;
    private BigDecimal amount;
    private String referenceNumber;
    private String account;
    private TransactionStatus status;
}
