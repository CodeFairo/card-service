package com.proyecto.wallet.infrastructure.mapper;

import com.proyecto.wallet.domain.model.Charge;
import com.proyecto.wallet.model.CreditChargeDTO;
import com.proyecto.wallet.model.CreditChargeRequestDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZoneOffset;

@Component
public class ChargeMapper {

    public Charge toDomain(CreditChargeRequestDTO dto) {
        return Charge.builder()
                .amount(BigDecimal.valueOf(dto.getAmount()))
                .merchant(dto.getMerchant())
                .description(dto.getDescription())
                .build();
    }

    public CreditChargeDTO toDto(Charge charge) {
        CreditChargeDTO dto = new CreditChargeDTO();
        dto.setId(charge.getId());
        dto.setCardId(charge.getCardId());
        dto.setAmount(charge.getAmount());
        dto.setMerchant(charge.getMerchant());
        dto.setDescription(charge.getDescription());
        dto.setChargeDate(charge.getChargeDate().atOffset(ZoneOffset.UTC));
        return dto;
    }
}
