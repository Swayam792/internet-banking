package com.swayam.banking.service;

import com.swayam.banking.dto.FundTransfer;
import com.swayam.banking.dto.TransactionStatus;
import com.swayam.banking.dto.request.FundTransferRequest;
import com.swayam.banking.dto.response.FundTransferResponse;
import com.swayam.banking.entity.FundTransferEntity;
import com.swayam.banking.mapper.FundTransferMapper;
import com.swayam.banking.repository.FundTransferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
@Slf4j
@Service
public class FundTransferService {
    private final FundTransferRepository fundTransferRepository;
    private FundTransferMapper mapper = new FundTransferMapper();

    @Autowired
    public FundTransferService(FundTransferRepository fundTransferRepository) {
        this.fundTransferRepository = fundTransferRepository;
    }

    public FundTransferResponse fundTransfer(FundTransferRequest request) {
        log.info("Sending fund transfer request {}" + request.toString());
        FundTransferEntity entity = new FundTransferEntity();
        BeanUtils.copyProperties(request, entity);
        entity.setStatus(TransactionStatus.PENDING);
        FundTransferEntity optFundTransfer = fundTransferRepository.save(entity);
        FundTransferResponse fundTransferResponse = new FundTransferResponse();
        fundTransferResponse.setTransactionId(UUID.randomUUID().toString());
        fundTransferResponse.setMessage("Success");
        return fundTransferResponse;
    }
    public List<FundTransfer> readAllTransfers(Pageable pageable) {
        return mapper.convertToDtoList(fundTransferRepository.findAll(pageable).getContent());
    }
}
