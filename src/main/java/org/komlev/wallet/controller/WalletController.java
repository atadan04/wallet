package org.komlev.wallet.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.komlev.wallet.dto.WalletOperationRequestDto;
import org.komlev.wallet.dto.WalletResponseDto;
import org.komlev.wallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/wallet")
    public ResponseEntity<WalletResponseDto> transaction(@RequestBody @Valid WalletOperationRequestDto walletRequestDto) {
        return ResponseEntity.ok(walletService.transaction(walletRequestDto));
    }

    @GetMapping("/wallets/{id}")
    public ResponseEntity<WalletResponseDto> getWalletById(@PathVariable UUID id) {
        return ResponseEntity.ok(walletService.getWalletById(id));
    }

}
