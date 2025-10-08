package com.proyecto.wallet.application.port.in.credit;

import com.proyecto.wallet.domain.model.Card;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FindCardUseCase {
    Flux<Card> findAll(String customerId, String cardType);
    Mono<Card> findById(String cardId);
}
