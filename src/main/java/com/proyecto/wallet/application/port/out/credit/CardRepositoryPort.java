package com.proyecto.wallet.application.port.out.credit;

import com.proyecto.wallet.domain.model.Card;
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
