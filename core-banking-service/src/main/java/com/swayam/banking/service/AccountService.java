package com.swayam.banking.service;

import com.swayam.banking.dto.BankAccount;
import com.swayam.banking.dto.UtilityAccount;
import com.swayam.banking.entity.BankAccountEntity;
import com.swayam.banking.entity.UtilityAccountEntity;
import com.swayam.banking.model.mapper.BankAccountMapper;
import com.swayam.banking.model.mapper.UtilityAccountMapper;
import com.swayam.banking.repository.BankAccountRepository;
import com.swayam.banking.repository.UtilityAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private BankAccountMapper bankAccountMapper = new BankAccountMapper();
    private UtilityAccountMapper utilityAccountMapper = new UtilityAccountMapper();

    private final BankAccountRepository bankAccountRepository;
    private final UtilityAccountRepository utilityAccountRepository;

    @Autowired
    public AccountService(BankAccountRepository bankAccountRepository, UtilityAccountRepository utilityAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.utilityAccountRepository = utilityAccountRepository;
    }

    public BankAccount readBankAccount(String accountNumber) {
        BankAccountEntity entity = bankAccountRepository.findByNumber(accountNumber).get();
        return bankAccountMapper.convertToDto(entity);
    }

    public UtilityAccount readUtilityAccount(String provider) {
        UtilityAccountEntity utilityAccountEntity = utilityAccountRepository.findByProviderName(provider).get();
        return utilityAccountMapper.convertToDto(utilityAccountEntity);
    }

    public UtilityAccount readUtilityAccount(Long id){
        return utilityAccountMapper.convertToDto(utilityAccountRepository.findById(id).get());
    }

}
