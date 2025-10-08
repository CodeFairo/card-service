package com.proyecto.wallet.infrastructure.adapter.in.web;

import com.proyecto.wallet.application.port.in.debit.RequestDebitDepositUseCase;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/debitcards")
@RequiredArgsConstructor
public class DebitDepositController {

    private final RequestDebitDepositUseCase useCase;

    @PostMapping("/{cardId}/deposits")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> requestDeposit(@PathVariable String cardId, @RequestBody DepositRequest req) {
        return useCase.requestDeposit(cardId, req.getAccountId(), req.getCustomerId(), req.getAmount(), req.getDescription());
    }

    @Data
    public static class DepositRequest {
        private String accountId;
        private String customerId;
        private BigDecimal amount;
        private String description;
    }
}
