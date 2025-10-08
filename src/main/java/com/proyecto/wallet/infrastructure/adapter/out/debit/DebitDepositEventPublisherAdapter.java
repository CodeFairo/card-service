package com.proyecto.wallet.infrastructure.adapter.out.debit;

import com.proyecto.account.avro.DebitCardDepositEvent;
import com.proyecto.wallet.application.port.out.debit.DebitDepositEventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DebitDepositEventPublisherAdapter implements DebitDepositEventPublisherPort {

    private static final String TOPIC = "debitcard.deposits";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public Mono<Void> publish(String cardId, String accountId, String customerId, BigDecimal amount, String description) {
        var event = DebitCardDepositEvent.newBuilder()
                .setCardId(cardId)
                .setAccountId(accountId)
                .setCustomerId(customerId)
                .setAmount(amount.doubleValue())
                .setDescription(description)
                .setTimestamp(OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .build();

        return Mono.fromFuture(kafkaTemplate.send(TOPIC, cardId, event).toCompletableFuture())
                .then();
    }
}
