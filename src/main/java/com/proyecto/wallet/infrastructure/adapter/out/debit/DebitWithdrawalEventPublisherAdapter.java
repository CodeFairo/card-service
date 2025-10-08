package com.proyecto.wallet.infrastructure.adapter.out.debit;

import com.proyecto.account.avro.DebitCardWithdrawalEvent;
import com.proyecto.wallet.application.port.out.debit.DebitWithdrawalEventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DebitWithdrawalEventPublisherAdapter implements DebitWithdrawalEventPublisherPort {

    private static final String TOPIC = "debitcard.withdrawals";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public Mono<Void> publish(String cardId, String accountId, String customerId, BigDecimal amount, String description) {
        var event = DebitCardWithdrawalEvent.newBuilder()
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
