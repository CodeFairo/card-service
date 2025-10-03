package com.proyecto.card.application.service;

import com.proyecto.card.application.port.in.*;
import com.proyecto.card.application.port.out.CardRepositoryPort;
import com.proyecto.card.domain.model.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CardService implements
        CreateCardUseCase,
        FindCardUseCase,
        DeleteCardUseCase,
        GetCardBalanceUseCase {

    private final CardRepositoryPort repository;

    @Override
    public Mono<Card> create(Card card) {
        card.setOutstandingBalance(BigDecimal.ZERO);
        card.setAvailableCredit(card.getCreditLimit());
        card.setStatus("ACTIVE");
        return repository.save(card);
    }

    @Override
    public Flux<Card> findAll(String customerId, String cardType) {
        if (customerId != null && cardType != null) {
            return repository.findByCustomerIdAndCardType(customerId, cardType);
        } else if (customerId != null) {
            return repository.findByCustomerId(customerId);
        }
        return repository.findAll();
    }

    @Override
    public Mono<Card> findById(String cardId) {
        return repository.findById(cardId);
    }

    @Override
    public Mono<Void> cancelCard(String cardId) {
        return repository.findById(cardId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Card not found")))
                .flatMap(card -> {
                    card.setStatus("CANCELLED");
                    return repository.save(card).then();
                });
    }

    @Override
    public Mono<Map<String, BigDecimal>> getBalance(String cardId) {
        return repository.findById(cardId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Card not found")))
                .map(card -> {
                    Map<String, BigDecimal> balance = new HashMap<>();
                    balance.put("creditLimit", card.getCreditLimit());
                    balance.put("outstandingBalance", card.getOutstandingBalance());
                    balance.put("availableCredit", card.getAvailableCredit());
                    return balance;
                });
    }
}
