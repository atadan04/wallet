package org.komlev.wallet.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletResponseDto(
        UUID id,
        BigDecimal balance
) {
}
