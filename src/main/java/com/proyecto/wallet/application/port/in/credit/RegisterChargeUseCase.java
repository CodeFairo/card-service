package com.proyecto.wallet.application.port.in.credit;

import com.proyecto.wallet.domain.model.Charge;
import reactor.core.publisher.Mono;

public interface RegisterChargeUseCase {
    Mono<Charge> register(String cardId, Charge charge);
}
