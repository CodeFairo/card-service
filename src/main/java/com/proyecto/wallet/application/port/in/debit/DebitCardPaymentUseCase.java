package com.proyecto.wallet.application.port.in.debit;

import com.proyecto.wallet.domain.model.DebitMovement;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface DebitCardPaymentUseCase {
    Mono<DebitMovement> pay(String cardId, BigDecimal amount, String description, String merchant);
}