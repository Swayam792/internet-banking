package com.swayam.banking.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Data
public class FundTransferRequest {
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
}
