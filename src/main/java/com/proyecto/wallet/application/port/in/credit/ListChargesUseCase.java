package com.proyecto.wallet.application.port.in.credit;

import com.proyecto.wallet.domain.model.Charge;
import reactor.core.publisher.Flux;

public interface ListChargesUseCase {
    Flux<Charge> listByCard(String cardId);
}
