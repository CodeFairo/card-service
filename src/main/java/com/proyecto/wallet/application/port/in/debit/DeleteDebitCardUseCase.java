package com.proyecto.wallet.application.port.in.debit;

import reactor.core.publisher.Mono;

public interface DeleteDebitCardUseCase {
    Mono<Void> delete(String cardId);
}