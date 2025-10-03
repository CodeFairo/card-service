package com.proyecto.card.application.port.out;

import com.proyecto.card.domain.model.Card;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CardRepositoryPort {
    Mono<Card> save(Card card);
    Flux<Card> findAll();
    Flux<Card> findByCustomerId(String customerId);
    Flux<Card> findByCustomerIdAndCardType(String customerId, String cardType);
    Mono<Card> findById(String id);
    Mono<Void> deleteById(String id);
}
