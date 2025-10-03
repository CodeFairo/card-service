package com.proyecto.card.application.port.in;

import com.proyecto.card.domain.model.Card;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FindCardUseCase {
    Flux<Card> findAll(String customerId, String cardType);
    Mono<Card> findById(String cardId);
}
