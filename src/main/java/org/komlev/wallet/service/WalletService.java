package org.komlev.wallet.service;

import jakarta.validation.Valid;
import org.komlev.wallet.OperationType;
import org.komlev.wallet.dto.WalletOperationRequestDto;
import org.komlev.wallet.dto.WalletOperationResponseDto;
import org.komlev.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public WalletOperationResponseDto transaction(WalletOperationRequestDto walletRequestDto) {

    }
}
    