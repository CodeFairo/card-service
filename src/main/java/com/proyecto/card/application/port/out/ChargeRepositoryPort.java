package com.proyecto.card.application.port.out;

import com.proyecto.card.domain.model.Charge;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChargeRepositoryPort {
    Mono<Charge> save(Charge charge);
    Flux<Charge> findByCardId(String cardId);
}
