package com.proyecto.wallet.application.port.in.debit;

import com.proyecto.wallet.domain.model.DebitCard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FindDebitCardUseCase {
    Flux<DebitCard> findAll(String customerId);
    Mono<DebitCard> findById(String cardId);
}
