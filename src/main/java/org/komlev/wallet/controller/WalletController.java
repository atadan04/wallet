package org.komlev.wallet.controller;

import jakarta.validation.Valid;
import org.komlev.wallet.dto.WalletOperationRequestDto;
import org.komlev.wallet.dto.WalletOperationResponseDto;
import org.komlev.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class WalletController {
    private final WalletService walletService;
    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/wallet")
    public ResponseEntity<WalletOperationResponseDto> transaction(@RequestBody @Valid WalletOperationRequestDto walletRequestDto){
       return ResponseEntity.status(HttpStatus.OK).body(walletService.transaction(walletRequestDto));
    }


}
