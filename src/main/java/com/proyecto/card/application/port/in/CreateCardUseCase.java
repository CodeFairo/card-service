package com.proyecto.card.application.port.in;

import com.proyecto.card.domain.model.Card;
import reactor.core.publisher.Mono;

public interface CreateCardUseCase {
    Mono<Card> create(Card card);
}
