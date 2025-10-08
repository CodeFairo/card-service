package com.proyecto.wallet.application.port.in.debit;

import com.proyecto.wallet.domain.model.DebitCard;
import reactor.core.publisher.Mono;

public interface CreateDebitCardUseCase {
    Mono<DebitCard> create(DebitCard card);
}
