package com.proyecto.wallet.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "debitcards")
public class DebitCard {

    @Id
    private String id;

    private String customerId;
    private String primaryAccountId;
    private List<AccountLink> linkedAccounts;
    private String status; // ACTIVE, BLOCKED, CANCELLED
    private LocalDate issuedAt;
    private String cardNumber;
}
