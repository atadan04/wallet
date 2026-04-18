package org.komlev.wallet.dto;

import org.komlev.wallet.OperationType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletOperationResponseDto(
        UUID walletId,
        OperationType operationType,
        BigDecimal amount
) {
}
