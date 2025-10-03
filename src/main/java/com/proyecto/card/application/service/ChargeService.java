package com.proyecto.card.application.service;

import com.proyecto.card.application.port.in.ListChargesUseCase;
import com.proyecto.card.application.port.in.RegisterChargeUseCase;
import com.proyecto.card.application.port.out.CardRepositoryPort;
import com.proyecto.card.application.port.out.ChargeRepositoryPort;
import com.proyecto.card.domain.model.Card;
import com.proyecto.card.domain.model.Charge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChargeService implements RegisterChargeUseCase, ListChargesUseCase {

    private final ChargeRepositoryPort chargeRepository;
    private final CardRepositoryPort cardRepository;

    @Override
    public Mono<Charge> register(String cardId, Charge charge) {
        return cardRepository.findById(cardId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Card not found")))
                .flatMap(card -> {
                    if (card.getAvailableCredit().compareTo(charge.getAmount()) < 0) {
                        return Mono.error(new IllegalStateException("Exceeds credit limit"));
                    }

                    // actualizar saldos
                    card.setOutstandingBalance(card.getOutstandingBalance().add(charge.getAmount()));
                    card.setAvailableCredit(card.getCreditLimit().subtract(card.getOutstandingBalance()));

                    charge.setCardId(cardId);
                    charge.setChargeDate(LocalDateTime.now());

                    return cardRepository.save(card)
                            .then(chargeRepository.save(charge));
                });
    }

    @Override
    public Flux<Charge> listByCard(String cardId) {
        return chargeRepository.findByCardId(cardId);
    }
}
