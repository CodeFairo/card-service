package com.proyecto.wallet.infrastructure.adapter.in.web;

import com.proyecto.wallet.application.port.in.debit.RequestDebitWithdrawalUseCase;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/debitcards")
@RequiredArgsConstructor
public class DebitWithdrawalController {

    private final RequestDebitWithdrawalUseCase useCase;

    @PostMapping("/{cardId}/withdrawals")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> requestWithdrawal(@PathVariable String cardId, @RequestBody WithdrawRequest req) {
        return useCase.requestWithdraw(cardId, req.getAccountId(), req.getCustomerId(), req.getAmount(), req.getDescription());
    }

    @Data
    public static class WithdrawRequest {
        private String accountId;
        private String customerId;
        private BigDecimal amount;
        private String description;
    }
}
