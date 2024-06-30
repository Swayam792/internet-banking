package com.swayam.banking.dto;

import com.swayam.banking.types.AccountStatus;
import com.swayam.banking.types.AccountType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankAccount {

    private Long id;
    private String number;
    private AccountType type;
    private AccountStatus status;
    private BigDecimal availableBalance;
    private BigDecimal actualBalance;
    private User user;

}
