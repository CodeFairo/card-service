package com.proyecto.card.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "charges")
public class Charge {

    @Id
    private String id;
    private String cardId;
    private BigDecimal amount;
    private String merchant;
    private String description;
    private LocalDateTime chargeDate;
}
