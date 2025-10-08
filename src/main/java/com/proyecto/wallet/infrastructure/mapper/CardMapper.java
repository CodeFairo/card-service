package com.proyecto.wallet.infrastructure.mapper;

import com.proyecto.wallet.domain.model.Card;
import com.proyecto.wallet.model.CreditCardDTO;
import com.proyecto.wallet.model.CreditCardRequestDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CardMapper {

    public Card toDomain(CreditCardRequestDTO dto) {
        return Card.builder()
                .cardType(dto.getCardType().name())
                .customerId(dto.getCustomerId())
                .creditLimit(BigDecimal.valueOf(dto.getCreditLimit()))
                .outstandingBalance(BigDecimal.ZERO)
                .availableCredit(BigDecimal.valueOf(dto.getCreditLimit()))
                .status("ACTIVE")
                .build();
    }

    public CreditCardDTO toDto(Card card) {
        CreditCardDTO dto = new CreditCardDTO();
        dto.setId(card.getId());
        dto.setCardType(card.getCardType());
        dto.setCustomerId(card.getCustomerId());
        dto.setCreditLimit(card.getCreditLimit().doubleValue());
        dto.setOutstandingBalance(card.getOutstandingBalance().doubleValue());
        dto.setAvailableCredit(card.getAvailableCredit().doubleValue());
        dto.setStatus(CreditCardDTO.StatusEnum.valueOf(card.getStatus()));
        return dto;
    }
}
