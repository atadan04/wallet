package org.komlev.wallet.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.komlev.wallet.enums.OperationType;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletOperationRequestDto(
        @NotNull
        UUID id,
        @NotNull
        OperationType operationType,
        @NotNull
        @Positive
        BigDecimal amount
) {
}
