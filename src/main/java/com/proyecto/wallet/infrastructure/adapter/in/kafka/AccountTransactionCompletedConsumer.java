package com.proyecto.wallet.infrastructure.adapter.in.kafka;

import com.proyecto.account.avro.AccountTransactionCompletedEvent;
import com.proyecto.wallet.application.port.out.debit.DebitMovementRepositoryPort;
import com.proyecto.wallet.domain.model.DebitMovement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountTransactionCompletedConsumer {

    private final DebitMovementRepositoryPort movementRepo;

    @KafkaListener(topics = "account.transactions.completed", groupId = "card-service")
    public void handleAccountTransaction(AccountTransactionCompletedEvent event) {
        log.info("Received AccountTransactionCompletedEvent: {}", event);

        DebitMovement movement = DebitMovement.builder()
                .cardId(event.getCardId().toString())
                .operationNumber(event.getOperationNumber().toString())
                .accountId(event.getAccountId().toString())
                .amount(BigDecimal.valueOf(event.getAmount()))
                .concept(event.getConcept().toString())
                .transactionType(event.getTransactionType().toString())
                .dateTime(OffsetDateTime.parse(event.getTimestamp(), DateTimeFormatter.ISO_OFFSET_DATE_TIME).toLocalDateTime())
                .build();

        Mono.just(movement)
                .flatMap(movementRepo::save)
                .doOnSuccess(saved -> log.info("Movement stored for card {} | op={}", saved.getCardId(), saved.getOperationNumber()))
                .doOnError(err -> log.error("Error saving movement: {}", err.getMessage()))
                .subscribe();
    }
}
