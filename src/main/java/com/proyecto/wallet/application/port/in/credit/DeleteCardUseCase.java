package com.proyecto.wallet.application.port.in.credit;

import reactor.core.publisher.Mono;

public interface DeleteCardUseCase {
    Mono<Void> cancelCard(String cardId);
}
