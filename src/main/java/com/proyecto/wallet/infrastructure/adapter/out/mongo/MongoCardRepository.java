package com.proyecto.wallet.infrastructure.adapter.out.mongo;

import com.proyecto.wallet.domain.model.Card;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MongoCardRepository extends ReactiveMongoRepository<Card, String> {
    Flux<Card> findByCustomerId(String customerId);
    Flux<Card> findByCustomerIdAndCardType(String customerId, String cardType);
}
