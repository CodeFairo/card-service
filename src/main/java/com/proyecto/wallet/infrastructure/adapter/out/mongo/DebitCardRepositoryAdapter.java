package com.proyecto.wallet.infrastructure.adapter.out.mongo;

import com.proyecto.wallet.application.port.out.debit.DebitCardRepositoryPort;
import com.proyecto.wallet.domain.model.DebitCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DebitCardRepositoryAdapter implements DebitCardRepositoryPort {

    private final DebitCardMongoRepository repository;

    @Override
    public Mono<DebitCard> save(DebitCard card) {
        return repository.save(card);
    }

    @Override
    public Mono<DebitCard> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<DebitCard> findAllByCustomerId(String customerId) {
        return repository.findByCustomerId(customerId);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }
}
