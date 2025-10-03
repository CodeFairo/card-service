package com.proyecto.card.application.port.in;

import com.proyecto.card.domain.model.Charge;
import reactor.core.publisher.Flux;

public interface ListChargesUseCase {
    Flux<Charge> listByCard(String cardId);
}
