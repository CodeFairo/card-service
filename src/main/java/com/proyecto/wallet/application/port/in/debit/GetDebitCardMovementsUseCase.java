package com.proyecto.wallet.application.port.in.debit;

import com.proyecto.wallet.domain.model.DebitMovement;
import reactor.core.publisher.Flux;

public interface GetDebitCardMovementsUseCase {
    Flux<DebitMovement> getLastMovements(String cardId);
}