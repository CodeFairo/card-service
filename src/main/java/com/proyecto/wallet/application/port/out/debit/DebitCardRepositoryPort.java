package com.proyecto.wallet.application.port.out.debit;

import com.proyecto.wallet.domain.model.DebitCard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DebitCardRepositoryPort {
    Mono<DebitCard> save(DebitCard card);
    Mono<DebitCard> findById(String id);
    Flux<DebitCard> findAllByCustomerId(String customerId);
    Mono<Void> deleteById(String id);
}

