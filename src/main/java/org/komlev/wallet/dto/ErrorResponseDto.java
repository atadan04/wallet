package org.komlev.wallet.dto;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        String message,
        String error,
        LocalDateTime timeStamp
) {

}
