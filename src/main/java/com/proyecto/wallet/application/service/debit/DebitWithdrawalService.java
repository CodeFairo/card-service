package com.proyecto.wallet.application.service.debit;

import com.proyecto.wallet.application.port.in.debit.RequestDebitWithdrawalUseCase;
import com.proyecto.wallet.application.port.out.debit.DebitWithdrawalEventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DebitWithdrawalService implements RequestDebitWithdrawalUseCase {

    private final DebitWithdrawalEventPublisherPort publisher;

    @Override
    public Mono<Void> requestWithdraw(String debitCardId, String accountId, String customerId, BigDecimal amount, String description) {
        // Aquí podrías validar status de la tarjeta, ownership, prioridad, etc.
        return publisher.publish(debitCardId, accountId, customerId, amount, description);
    }
}
