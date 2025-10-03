package com.proyecto.card.application.port.in;

import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.util.Map;

public interface GetCardBalanceUseCase {
    Mono<Map<String, BigDecimal>> getBalance(String cardId);
}
