package com.proyecto.wallet.application.port.out.account;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface AccountServicePort {
    Mono<BigDecimal> getAccountBalance(String accountId);
}
