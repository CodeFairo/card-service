package com.proyecto.wallet.application.port.out.debit;

import com.proyecto.wallet.domain.model.DebitMovement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DebitMovementRepositoryPort {

    Mono<DebitMovement> save(DebitMovement movement);

    Flux<DebitMovement> findTop10ByCardIdOrderByDateTimeDesc(String cardId);
}
