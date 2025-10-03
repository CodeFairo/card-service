package com.proyecto.card.infrastructure.adapter.out.mongo;

import com.proyecto.card.application.port.out.ChargeRepositoryPort;
import com.proyecto.card.domain.model.Charge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ChargeRepositoryAdapter implements ChargeRepositoryPort {

    private final MongoChargeRepository repository;

    @Override
    public Mono<Charge> save(Charge charge) {
        return repository.save(charge);
    }

    @Override
    public Flux<Charge> findByCardId(String cardId) {
        return repository.findByCardId(cardId);
    }
}
