package com.swayam.banking.controller;

import com.swayam.banking.dto.request.UtilityPaymentRequest;
import com.swayam.banking.service.UtilityPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/utility-payment")
public class UtilityPaymentController {

    private final UtilityPaymentService utilityPaymentService;

    @Autowired
    public UtilityPaymentController(UtilityPaymentService utilityPaymentService) {
        this.utilityPaymentService = utilityPaymentService;
    }

    @GetMapping
    public ResponseEntity readPayments(Pageable pageable) {
        return ResponseEntity.ok(utilityPaymentService.readPayments(pageable));
    }

    @PostMapping
    public ResponseEntity processPayment(@RequestBody UtilityPaymentRequest paymentRequest) {
        return ResponseEntity.ok(utilityPaymentService.utilPayment(paymentRequest));
    }

}
