package com.proyecto.wallet.application.service.debit;

import com.proyecto.wallet.application.port.in.debit.*;
import com.proyecto.wallet.application.port.out.account.AccountServicePort;
import com.proyecto.wallet.application.port.out.debit.DebitCardRepositoryPort;
import com.proyecto.wallet.application.port.out.debit.DebitMovementRepositoryPort;
import com.proyecto.wallet.domain.model.DebitCard;
import com.proyecto.wallet.domain.model.DebitMovement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DebitCardService implements
        CreateDebitCardUseCase,
        FindDebitCardUseCase,
        DeleteDebitCardUseCase,
        GetDebitCardBalanceUseCase,
        GetDebitCardMovementsUseCase {

    private final DebitCardRepositoryPort repository;
    private final DebitMovementRepositoryPort movementRepository;
    private final AccountServicePort accountServicePort;

    @Override
    public Mono<DebitCard> create(DebitCard card) {
        card.setIssuedAt(LocalDate.now());
        card.setStatus("ACTIVE");
        return repository.save(card);
    }

    @Override
    public Flux<DebitCard> findAll(String customerId) {
        return repository.findAllByCustomerId(customerId);
    }

    @Override
    public Mono<DebitCard> findById(String cardId) {
        return repository.findById(cardId);
    }

    @Override
    public Mono<Void> delete(String cardId) {
        return repository.deleteById(cardId);
    }

    @Override
    public Flux<DebitMovement> getLastMovements(String cardId) {
        return repository.findById(cardId)
                .flatMapMany(card -> movementRepository.findTop10ByCardIdOrderByDateTimeDesc(cardId))
                .switchIfEmpty(Flux.error(new IllegalArgumentException("Card not found or no movements")));
    }

    @Override
    public Mono<BigDecimal> getPrimaryBalance(String cardId) {
        return repository.findById(cardId)
                .flatMap(card -> accountServicePort.getAccountBalance(card.getPrimaryAccountId()));
    }
}
