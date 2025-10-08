package com.proyecto.wallet.domain.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountLink {
    private String accountId;
    private Integer priorityOrder;
}
