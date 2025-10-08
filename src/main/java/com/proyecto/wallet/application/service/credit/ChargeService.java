package com.proyecto.wallet.application.service.credit;

import com.proyecto.wallet.application.port.in.credit.ListChargesUseCase;
import com.proyecto.wallet.application.port.in.credit.RegisterChargeUseCase;
import com.proyecto.wallet.application.port.out.credit.CardRepositoryPort;
import com.proyecto.wallet.application.port.out.credit.ChargeRepositoryPort;
import com.proyecto.wallet.domain.model.Charge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChargeService implements RegisterChargeUseCase, ListChargesUseCase {

    private final ChargeRepositoryPort chargeRepository;
    private final CardRepositoryPort cardRepository;

    @Override
    public Mono<Charge> register(String cardId, Charge charge) {
        return cardRepository.findById(cardId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Card not found")))
                .flatMap(card -> {
                    if (card.getAvailableCredit().compareTo(charge.getAmount()) < 0) {
                        return Mono.error(new IllegalStateException("Exceeds credit limit"));
                    }

                    // actualizar saldos
                    card.setOutstandingBalance(card.getOutstandingBalance().add(charge.getAmount()));
                    card.setAvailableCredit(card.getCreditLimit().subtract(card.getOutstandingBalance()));

                    charge.setCardId(cardId);
                    charge.setChargeDate(LocalDateTime.now());

                    return cardRepository.save(card)
                            .then(chargeRepository.save(charge));
                });
    }

    @Override
    public Flux<Charge> listByCard(String cardId) {
        return chargeRepository.findByCardId(cardId);
    }
}
