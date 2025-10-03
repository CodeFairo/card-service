package com.proyecto.card.infrastructure.mapper;

import com.proyecto.card.domain.model.Charge;
import com.proyecto.card.model.ChargeDTO;
import com.proyecto.card.model.ChargeRequestDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class ChargeMapper {

    public Charge toDomain(ChargeRequestDTO dto) {
        return Charge.builder()
                .amount(BigDecimal.valueOf(dto.getAmount()))
                .merchant(dto.getMerchant())
                .description(dto.getDescription())
                .build();
    }

    public ChargeDTO toDto(Charge charge) {
        ChargeDTO dto = new ChargeDTO();
        dto.setId(charge.getId());
        dto.setCardId(charge.getCardId());
        dto.setAmount(charge.getAmount().doubleValue());
        dto.setMerchant(charge.getMerchant());
        dto.setDescription(charge.getDescription());
        dto.setChargeDate(charge.getChargeDate().atOffset(ZoneOffset.UTC));
        return dto;
    }
}
