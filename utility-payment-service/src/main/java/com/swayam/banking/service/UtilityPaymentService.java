package com.swayam.banking.service;

import com.swayam.banking.dto.TransactionStatus;
import com.swayam.banking.dto.UtilityPayment;
import com.swayam.banking.dto.request.UtilityPaymentRequest;
import com.swayam.banking.dto.response.UtilityPaymentResponse;
import com.swayam.banking.entity.UtilityPaymentEntity;
import com.swayam.banking.mapper.UtilityPaymentMapper;
import com.swayam.banking.repository.UtilityPaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UtilityPaymentService {
    private final UtilityPaymentRepository utilityPaymentRepository;

    private UtilityPaymentMapper utilityPaymentMapper = new UtilityPaymentMapper();
    @Autowired
    public UtilityPaymentService(UtilityPaymentRepository utilityPaymentRepository) {
        this.utilityPaymentRepository = utilityPaymentRepository;
    }

    public UtilityPaymentResponse utilPayment(UtilityPaymentRequest paymentRequest) {
        log.info("Utility payment processing {}", paymentRequest.toString());

        UtilityPaymentEntity entity = new UtilityPaymentEntity();
        BeanUtils.copyProperties(paymentRequest, entity);
        entity.setStatus(TransactionStatus.PROCESSING);
        UtilityPaymentEntity optUtilPayment = utilityPaymentRepository.save(entity);

        String transactionId = UUID.randomUUID().toString();
        optUtilPayment.setStatus(TransactionStatus.SUCCESS);
        optUtilPayment.setTransactionId(transactionId);

        utilityPaymentRepository.save(optUtilPayment);

        return UtilityPaymentResponse.builder().message("Utility Payment Successfully Processed").transactionId(transactionId).build();
    }

    public List<UtilityPayment> readPayments(Pageable pageable) {
        Page<UtilityPaymentEntity> allUtilPayments = utilityPaymentRepository.findAll(pageable);
        return utilityPaymentMapper.convertToDtoList(allUtilPayments.getContent());
    }
}
