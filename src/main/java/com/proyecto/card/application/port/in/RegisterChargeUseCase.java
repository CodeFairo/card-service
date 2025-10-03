package com.proyecto.card.application.port.in;

import com.proyecto.card.domain.model.Charge;
import reactor.core.publisher.Mono;

public interface RegisterChargeUseCase {
    Mono<Charge> register(String cardId, Charge charge);
}
