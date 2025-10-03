package com.proyecto.card.application.port.in;

import reactor.core.publisher.Mono;

public interface DeleteCardUseCase {
    Mono<Void> cancelCard(String cardId);
}
