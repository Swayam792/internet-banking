package com.swayam.banking.repository;

import com.swayam.banking.dto.UtilityPayment;
import com.swayam.banking.entity.UtilityPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilityPaymentRepository extends JpaRepository<UtilityPaymentEntity, UtilityPayment> {
}
