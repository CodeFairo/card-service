package com.proyecto.wallet.application.port.in.debit;

import reactor.core.publisher.Mono;
import java.math.BigDecimal;

public interface RequestDebitDepositUseCase {
    Mono<Void> requestDeposit(String debitCardId, String accountId, String customerId, BigDecimal amount, String description);
}
