package org.komlev.wallet.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.komlev.wallet.enums.OperationType;
import org.komlev.wallet.dto.WalletOperationRequestDto;
import org.komlev.wallet.dto.WalletResponseDto;
import org.komlev.wallet.mapper.WalletMapper;
import org.komlev.wallet.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletService {
    private final WalletRepository walletRepository;
    private final WalletMapper mapper;

    public WalletResponseDto transaction(WalletOperationRequestDto walletOperationRequestDto) {
        var wallet = walletRepository.findById(walletOperationRequestDto.id())
                .orElseThrow(EntityNotFoundException::new);
        if (walletOperationRequestDto.operationType().equals(OperationType.WITHDRAW)
                && wallet.getBalance().compareTo(walletOperationRequestDto.amount()) < 0) {
            throw new IllegalArgumentException("The balance is less than the withdrawal amount");
        } else if (walletOperationRequestDto.operationType().equals(OperationType.WITHDRAW)) {
            wallet.setBalance(wallet.getBalance().subtract(walletOperationRequestDto.amount()));
        } else if (walletOperationRequestDto.operationType().equals(OperationType.DEPOSIT)) {
            wallet.setBalance(wallet.getBalance().add(walletOperationRequestDto.amount()));
        }

        log.info("Transaction successfully! Wallet id - {}", walletOperationRequestDto.id());
        walletRepository.save(wallet);
       return mapper.toResponseDto(wallet);
    }

    public WalletResponseDto getWalletById(UUID id) {
        var walletEntity = walletRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found by id - " + id));
        return mapper.toResponseDto(walletEntity);

    }
}
    