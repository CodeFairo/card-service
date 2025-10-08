package com.proyecto.wallet.infrastructure.adapter.in.web;

import com.proyecto.wallet.api.CreditCardChargesApi;
import com.proyecto.wallet.application.port.in.credit.ListChargesUseCase;
import com.proyecto.wallet.application.port.in.credit.RegisterChargeUseCase;
import com.proyecto.wallet.infrastructure.mapper.ChargeMapper;
import com.proyecto.wallet.model.CreditChargeDTO;
import com.proyecto.wallet.model.CreditChargeRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CreditCardChargeController implements CreditCardChargesApi {

    private final RegisterChargeUseCase registerChargeUseCase;
    private final ListChargesUseCase listChargesUseCase;
    private final ChargeMapper mapper;

    @Override
    public Mono<ResponseEntity<Flux<CreditChargeDTO>>> creditcardsCardIdChargesGet(String cardId, ServerWebExchange exchange) {
        Flux<CreditChargeDTO> charges = listChargesUseCase.listByCard(cardId).map(mapper::toDto);
        return Mono.just(ResponseEntity.ok(charges));
    }

    @Override
    public Mono<ResponseEntity<CreditChargeDTO>> creditcardsCardIdChargesPost(String cardId, Mono<CreditChargeRequestDTO> chargeRequestDTO, ServerWebExchange exchange) {
        return chargeRequestDTO
                .map(mapper::toDomain)
                .flatMap(charge -> registerChargeUseCase.register(cardId, charge))
                .map(mapper::toDto)
                .map(saved -> ResponseEntity.status(201).body(saved));
    }
}
