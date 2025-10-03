package com.proyecto.card.infrastructure.adapter.in.web;

import com.proyecto.card.api.CardsApi;
import com.proyecto.card.application.port.in.*;
import com.proyecto.card.infrastructure.mapper.CardMapper;
import com.proyecto.card.model.CardDTO;
import com.proyecto.card.model.CardRequestDTO;
import com.proyecto.card.model.CardsCardIdBalanceGet200Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CardController implements CardsApi {

    private final CreateCardUseCase createCardUseCase;
    private final FindCardUseCase findCardUseCase;
    private final DeleteCardUseCase deleteCardUseCase;
    private final GetCardBalanceUseCase getCardBalanceUseCase;
    private final CardMapper mapper;

    @Override
    public Mono<ResponseEntity<CardDTO>> cardsPost(Mono<CardRequestDTO> cardRequestDTO, ServerWebExchange exchange) {
        return cardRequestDTO
                .map(mapper::toDomain)
                .flatMap(createCardUseCase::create)
                .map(mapper::toDto)
                .map(saved -> ResponseEntity.status(201).body(saved));
    }

    @Override
    public Mono<ResponseEntity<Flux<CardDTO>>> cardsGet(String customerId, String cardType, ServerWebExchange exchange) {
        Flux<CardDTO> cards = findCardUseCase.findAll(customerId, cardType).map(mapper::toDto);
        return Mono.just(ResponseEntity.ok(cards));
    }

    @Override
    public Mono<ResponseEntity<CardDTO>> cardsCardIdGet(String cardId, ServerWebExchange exchange) {
        return findCardUseCase.findById(cardId)
                .map(mapper::toDto)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> cardsCardIdDelete(String cardId, ServerWebExchange exchange) {
        return deleteCardUseCase.cancelCard(cardId)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @Override
    public Mono<ResponseEntity<CardsCardIdBalanceGet200Response>> cardsCardIdBalanceGet(String cardId, ServerWebExchange exchange) {
        return getCardBalanceUseCase.getBalance(cardId)
                .map(balance -> {
                    CardsCardIdBalanceGet200Response dto = new CardsCardIdBalanceGet200Response();
                    dto.setCardId(cardId);
                    dto.setCreditLimit(balance.get("creditLimit").doubleValue());
                    dto.setOutstandingBalance(balance.get("outstandingBalance").doubleValue());
                    dto.setAvailableCredit(balance.get("availableCredit").doubleValue());
                    return ResponseEntity.ok(dto);
                });
    }
}
