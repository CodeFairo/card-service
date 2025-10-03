package com.proyecto.card.infrastructure.mapper;

import com.proyecto.card.domain.model.Card;
import com.proyecto.card.model.CardDTO;
import com.proyecto.card.model.CardRequestDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CardMapper {

    public Card toDomain(CardRequestDTO dto) {
        return Card.builder()
                .cardType(dto.getCardType().name())
                .customerId(dto.getCustomerId())
                .creditLimit(BigDecimal.valueOf(dto.getCreditLimit()))
                .outstandingBalance(BigDecimal.ZERO)
                .availableCredit(BigDecimal.valueOf(dto.getCreditLimit()))
                .status("ACTIVE")
                .build();
    }

    public CardDTO toDto(Card card) {
        CardDTO dto = new CardDTO();
        dto.setId(card.getId());
        dto.setCardType(card.getCardType());
        dto.setCustomerId(card.getCustomerId());
        dto.setCreditLimit(card.getCreditLimit().doubleValue());
        dto.setOutstandingBalance(card.getOutstandingBalance().doubleValue());
        dto.setAvailableCredit(card.getAvailableCredit().doubleValue());
        dto.setStatus(CardDTO.StatusEnum.valueOf(card.getStatus()));
        return dto;
    }
}
