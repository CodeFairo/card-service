package com.proyecto.wallet.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "debit_movements")
public class DebitMovement {

    @Id
    private String id;

    private String cardId;
    private String operationNumber;
    private String accountId;
    private BigDecimal amount;
    private String concept;
    private String transactionType;
    private LocalDateTime dateTime;
}
