package com.proyecto.wallet.application.port.out.credit;

import reactor.core.publisher.Mono;

public interface CreditServicePort {
    Mono<Boolean> hasAnyCredit(String customerId);
}
