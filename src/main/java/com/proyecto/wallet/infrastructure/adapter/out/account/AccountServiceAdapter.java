package com.proyecto.wallet.infrastructure.adapter.out.account;

import com.proyecto.wallet.application.port.out.account.AccountServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class AccountServiceAdapter implements AccountServicePort {

    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<BigDecimal> getAccountBalance(String accountId) {
        return webClientBuilder
                .baseUrl("http://localhost:8086/api/v1/accounts")
                .build()
                .get()
                .uri("/{accountId}/balance", accountId)
                .retrieve()
                .bodyToMono(AccountBalanceResponse.class)
                .map(response -> BigDecimal.valueOf(response.availableBalance()));
    }

    private record AccountBalanceResponse(
            String accountId,
            Double availableBalance
    ) {}
}
