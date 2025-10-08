package com.proyecto.wallet.application.service.credit;

import com.proyecto.wallet.application.port.in.credit.CreateCardUseCase;
import com.proyecto.wallet.application.port.in.credit.DeleteCardUseCase;
import com.proyecto.wallet.application.port.in.credit.FindCardUseCase;
import com.proyecto.wallet.application.port.in.credit.GetCardBalanceUseCase;
import com.proyecto.wallet.application.port.out.credit.CardRepositoryPort;
import com.proyecto.wallet.application.port.out.credit.CreditServicePort;
import com.proyecto.wallet.domain.model.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CardService implements
        CreateCardUseCase,
        FindCardUseCase,
        DeleteCardUseCase,
        GetCardBalanceUseCase {

    private final CardRepositoryPort repository;
    private final CreditServicePort creditServicePort;

    @Override
    public Mono<Card> create(Card card) {
        String customerId = card.getCustomerId();

        return creditServicePort.hasAnyCredit(customerId)
                .flatMap(hasCredit -> {
                    if (hasCredit) {
                        return Mono.error(new IllegalArgumentException(
                                "The client cannot request a credit card if he or she has expired credits."));
                    }

                    card.setOutstandingBalance(BigDecimal.ZERO);
                    card.setAvailableCredit(card.getCreditLimit());
                    card.setStatus("ACTIVE");
                    return repository.save(card);
                });
    }

    @Override
    public Flux<Card> findAll(String customerId, String cardType) {
        if (customerId != null && cardType != null) {
            return repository.findByCustomerIdAndCardType(customerId, cardType);
        } else if (customerId != null) {
            return repository.findByCustomerId(customerId);
        }
        return repository.findAll();
    }

    @Override
    public Mono<Card> findById(String cardId) {
        return repository.findById(cardId);
    }

    @Override
    public Mono<Void> cancelCard(String cardId) {
        return repository.findById(cardId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Card not found")))
                .flatMap(card -> {
                    card.setStatus("CANCELLED");
                    return repository.save(card).then();
                });
    }

    @Override
    public Mono<Map<String, BigDecimal>> getBalance(String cardId) {
        return repository.findById(cardId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Card not found")))
                .map(card -> {
                    Map<String, BigDecimal> balance = new HashMap<>();
                    balance.put("creditLimit", card.getCreditLimit());
                    balance.put("outstandingBalance", card.getOutstandingBalance());
                    balance.put("availableCredit", card.getAvailableCredit());
                    return balance;
                });
    }
}
