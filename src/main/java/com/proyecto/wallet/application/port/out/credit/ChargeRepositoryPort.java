package com.proyecto.wallet.application.port.out.credit;

import com.proyecto.wallet.domain.model.Charge;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChargeRepositoryPort {
    Mono<Charge> save(Charge charge);
    Flux<Charge> findByCardId(String cardId);
}
