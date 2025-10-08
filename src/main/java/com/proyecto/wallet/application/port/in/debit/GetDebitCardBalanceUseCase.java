package com.proyecto.wallet.application.port.in.debit;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface GetDebitCardBalanceUseCase {
    Mono<BigDecimal> getPrimaryBalance(String cardId);
}