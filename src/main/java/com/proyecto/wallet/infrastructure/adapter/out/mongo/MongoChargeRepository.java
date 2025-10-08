package com.proyecto.wallet.infrastructure.adapter.out.mongo;

import com.proyecto.wallet.domain.model.Charge;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MongoChargeRepository extends ReactiveMongoRepository<Charge, String> {
    Flux<Charge> findByCardId(String cardId);
}
