package com.proyecto.wallet.infrastructure.adapter.in.web;

import com.proyecto.wallet.api.CreditCardsApi;
import com.proyecto.wallet.application.port.in.credit.CreateCardUseCase;
import com.proyecto.wallet.application.port.in.credit.DeleteCardUseCase;
import com.proyecto.wallet.application.port.in.credit.FindCardUseCase;
import com.proyecto.wallet.application.port.in.credit.GetCardBalanceUseCase;
import com.proyecto.wallet.infrastructure.mapper.CardMapper;
import com.proyecto.wallet.model.CreditBalanceDTO;
import com.proyecto.wallet.model.CreditCardDTO;
import com.proyecto.wallet.model.CreditCardRequestDTO;
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
public class CreditCardController implements CreditCardsApi {

    private final CreateCardUseCase createCardUseCase;
    private final FindCardUseCase findCardUseCase;
    private final DeleteCardUseCase deleteCardUseCase;
    private final GetCardBalanceUseCase getCardBalanceUseCase;
    private final CardMapper mapper;

    @Override
    public Mono<ResponseEntity<CreditCardDTO>> creditcardsPost(Mono<CreditCardRequestDTO> cardRequestDTO, ServerWebExchange exchange) {
        return cardRequestDTO
                .map(mapper::toDomain)
                .flatMap(createCardUseCase::create)
                .map(mapper::toDto)
                .map(saved -> ResponseEntity.status(201).body(saved));
    }

    @Override
    public Mono<ResponseEntity<Flux<CreditCardDTO>>> creditcardsGet(String customerId, String cardType, ServerWebExchange exchange) {
        Flux<CreditCardDTO> cards = findCardUseCase.findAll(customerId, cardType).map(mapper::toDto);
        return Mono.just(ResponseEntity.ok(cards));
    }

    @Override
    public Mono<ResponseEntity<CreditCardDTO>> creditcardsCardIdGet(String cardId, ServerWebExchange exchange) {
        return findCardUseCase.findById(cardId)
                .map(mapper::toDto)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> creditcardsCardIdDelete(String cardId, ServerWebExchange exchange) {
        return deleteCardUseCase.cancelCard(cardId)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @Override
    public Mono<ResponseEntity<CreditBalanceDTO>> creditcardsCardIdBalanceGet(String cardId, ServerWebExchange exchange) {
        return getCardBalanceUseCase.getBalance(cardId)
                .map(balance -> {
                    CreditBalanceDTO dto = new CreditBalanceDTO();
                    dto.setCardId(cardId);
                    dto.setCreditLimit(balance.get("creditLimit"));
                    dto.setOutstandingBalance(balance.get("outstandingBalance"));
                    dto.setAvailableCredit(balance.get("availableCredit"));
                    return ResponseEntity.ok(dto);
                });
    }
}
