package com.proyecto.wallet.infrastructure.adapter.out.mongo;

import com.proyecto.wallet.application.port.out.debit.DebitMovementRepositoryPort;
import com.proyecto.wallet.domain.model.DebitMovement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DebitMovementMongoAdapter implements DebitMovementRepositoryPort {

    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<DebitMovement> save(DebitMovement movement) {
        return mongoTemplate.save(movement);
    }

    @Override
    public Flux<DebitMovement> findTop10ByCardIdOrderByDateTimeDesc(String cardId) {
        Query query = new Query(Criteria.where("cardId").is(cardId))
                .with(Sort.by(Sort.Direction.DESC, "dateTime"))
                .limit(10);
        return mongoTemplate.find(query, DebitMovement.class);
    }
}
