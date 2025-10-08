package com.proyecto.wallet.application.port.in.credit;

import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.util.Map;

public interface GetCardBalanceUseCase {
    Mono<Map<String, BigDecimal>> getBalance(String cardId);
}
