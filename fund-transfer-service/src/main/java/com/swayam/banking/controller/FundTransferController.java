package com.swayam.banking.controller;

import com.swayam.banking.dto.request.FundTransferRequest;
import com.swayam.banking.service.FundTransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/transfer")
public class FundTransferController {
    private final FundTransferService fundTransferService;
    @Autowired
    public FundTransferController(FundTransferService fundTransferService) {
        this.fundTransferService = fundTransferService;
    }

    @PostMapping
    public ResponseEntity sendFundTransfer(@RequestBody FundTransferRequest fundTransferRequest) {
        log.info("Got fund transfer request from API {}", fundTransferRequest.toString());
        return ResponseEntity.ok(fundTransferService.fundTransfer(fundTransferRequest));
    }
    @GetMapping
    public ResponseEntity readFundTransfers (Pageable pageable) {
        log.info("Reading fund transfers from core");
        return ResponseEntity.ok(fundTransferService.readAllTransfers(pageable));
    }
}
