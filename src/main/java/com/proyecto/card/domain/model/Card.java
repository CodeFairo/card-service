package com.proyecto.card.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cards")
public class Card {

    @Id
    private String id;
    private String cardType; // PERSONAL | BUSINESS
    private String customerId;
    private BigDecimal creditLimit;
    private BigDecimal outstandingBalance;
    private BigDecimal availableCredit;
    private String status; // ACTIVE | BLOCKED | CANCELLED
}
