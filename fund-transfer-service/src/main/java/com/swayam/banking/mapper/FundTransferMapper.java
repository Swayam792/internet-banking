package com.swayam.banking.mapper;

import com.swayam.banking.dto.FundTransfer;
import com.swayam.banking.entity.FundTransferEntity;
import org.springframework.beans.BeanUtils;
public class FundTransferMapper extends BaseMapper<FundTransferEntity, FundTransfer> {
    @Override
    public FundTransferEntity convertToEntity(FundTransfer dto, Object... args) {
        FundTransferEntity entity = new FundTransferEntity();
        if (dto != null) {
            BeanUtils.copyProperties(dto, entity);
        }
        return entity;
    }
    @Override
    public FundTransfer convertToDto(FundTransferEntity entity, Object... args) {
        FundTransfer dto = new FundTransfer();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto);
        }
        return dto;
    }
}
