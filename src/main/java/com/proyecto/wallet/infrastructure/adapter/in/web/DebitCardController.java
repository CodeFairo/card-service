package com.proyecto.wallet.infrastructure.adapter.in.web;

import com.proyecto.wallet.application.port.in.debit.*;
import com.proyecto.wallet.domain.model.DebitCard;
import com.proyecto.wallet.domain.model.DebitMovement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/debitcards")
@RequiredArgsConstructor
public class DebitCardController {

    private final CreateDebitCardUseCase createUseCase;
    private final FindDebitCardUseCase findUseCase;
    private final DeleteDebitCardUseCase deleteUseCase;
    //private final DebitCardPaymentUseCase paymentUseCase;
    private final GetDebitCardBalanceUseCase balanceUseCase;
    private final GetDebitCardMovementsUseCase movementUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<DebitCard> create(@RequestBody DebitCard card) {
        return createUseCase.create(card);
    }

    @GetMapping
    public Flux<DebitCard> findAll(@RequestParam(required = false) String customerId) {
        return findUseCase.findAll(customerId);
    }

    @GetMapping("/{id}")
    public Mono<DebitCard> findById(@PathVariable String id) {
        return findUseCase.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable String id) {
        return deleteUseCase.delete(id);
    }

    @GetMapping("/{id}/balance")
    public Mono<BigDecimal> getBalance(@PathVariable String id) {
        return balanceUseCase.getPrimaryBalance(id);
    }

    /*@PostMapping("/{id}/payments")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<DebitMovement> pay(@PathVariable String id,
                                   @RequestParam BigDecimal amount,
                                   @RequestParam(required = false) String description,
                                   @RequestParam(required = false) String merchant) {
        return paymentUseCase.pay(id, amount, description, merchant);
    }*/

    @GetMapping("/{id}/movements")
    public Flux<DebitMovement> getMovements(@PathVariable String id) {
        return movementUseCase.getLastMovements(id);
    }

}
