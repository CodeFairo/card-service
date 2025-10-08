package com.proyecto.wallet.application.service.debit;

import com.proyecto.wallet.application.port.in.debit.RequestDebitDepositUseCase;
import com.proyecto.wallet.application.port.out.debit.DebitDepositEventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DebitDepositService implements RequestDebitDepositUseCase {

    private final DebitDepositEventPublisherPort publisher;

    @Override
    public Mono<Void> requestDeposit(String debitCardId, String accountId, String customerId, BigDecimal amount, String description) {
        // Aquí podrías validar estado de la tarjeta, límites, ownership, etc.
        return publisher.publish(debitCardId, accountId, customerId, amount, description);
    }
}
