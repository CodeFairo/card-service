package com.proyecto.wallet.application.port.out.debit;

import reactor.core.publisher.Mono;
import java.math.BigDecimal;

public interface DebitDepositEventPublisherPort {
    Mono<Void> publish(String cardId, String accountId, String customerId, BigDecimal amount, String description);
}
