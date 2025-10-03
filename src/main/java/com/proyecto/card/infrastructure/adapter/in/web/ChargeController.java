package com.proyecto.card.infrastructure.adapter.in.web;

import com.proyecto.card.api.ChargesApi;
import com.proyecto.card.application.port.in.ListChargesUseCase;
import com.proyecto.card.application.port.in.RegisterChargeUseCase;
import com.proyecto.card.infrastructure.mapper.ChargeMapper;
import com.proyecto.card.model.ChargeDTO;
import com.proyecto.card.model.ChargeRequestDTO;
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
public class ChargeController implements ChargesApi {

    private final RegisterChargeUseCase registerChargeUseCase;
    private final ListChargesUseCase listChargesUseCase;
    private final ChargeMapper mapper;

    @Override
    public Mono<ResponseEntity<Flux<ChargeDTO>>> cardsCardIdChargesGet(String cardId, ServerWebExchange exchange) {
        Flux<ChargeDTO> charges = listChargesUseCase.listByCard(cardId).map(mapper::toDto);
        return Mono.just(ResponseEntity.ok(charges));
    }

    @Override
    public Mono<ResponseEntity<ChargeDTO>> cardsCardIdChargesPost(String cardId, Mono<ChargeRequestDTO> chargeRequestDTO, ServerWebExchange exchange) {
        return chargeRequestDTO
                .map(mapper::toDomain)
                .flatMap(charge -> registerChargeUseCase.register(cardId, charge))
                .map(mapper::toDto)
                .map(saved -> ResponseEntity.status(201).body(saved));
    }
}
