package com.proyecto.card.infrastructure.adapter.out.mongo;

import com.proyecto.card.application.port.out.CardRepositoryPort;
import com.proyecto.card.domain.model.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CardRepositoryAdapter implements CardRepositoryPort {

    private final MongoCardRepository repository;

    @Override
    public Mono<Card> save(Card card) {
        return repository.save(card);
    }

    @Override
    public Flux<Card> findAll() {
        return repository.findAll();
    }

    @Override
    public Flux<Card> findByCustomerId(String customerId) {
        return repository.findByCustomerId(customerId);
    }

    @Override
    public Flux<Card> findByCustomerIdAndCardType(String customerId, String cardType) {
        return repository.findByCustomerIdAndCardType(customerId, cardType);
    }

    @Override
    public Mono<Card> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }
}
