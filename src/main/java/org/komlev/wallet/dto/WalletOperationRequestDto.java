package org.komlev.wallet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.UniqueElements;
import org.komlev.wallet.OperationType;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletOperationRequestDto(
        @NotNull
        UUID walletId,
        @NotNull
        OperationType operationType,
        @NotNull
        @Positive
        BigDecimal amount
) {
}
