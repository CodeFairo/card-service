package com.proyecto.wallet.application.port.in.credit;

import com.proyecto.wallet.domain.model.Card;
import reactor.core.publisher.Mono;

public interface CreateCardUseCase {
    Mono<Card> create(Card card);
}
