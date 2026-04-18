package org.komlev.wallet.service;

import jakarta.persistence.EntityNotFoundException;
import org.komlev.wallet.OperationType;
import org.komlev.wallet.dto.WalletOperationRequestDto;
import org.komlev.wallet.dto.WalletResponseDto;
import org.komlev.wallet.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private static final Logger log = LoggerFactory.getLogger(WalletService.class);
    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void transaction(WalletOperationRequestDto walletOperationRequestDto) {
        var temp = walletRepository.findById(walletOperationRequestDto.walletId()).orElseThrow(EntityNotFoundException::new);
        if(walletOperationRequestDto.operationType().equals(OperationType.WITHDRAW)
                &&temp.getBalance().compareTo(walletOperationRequestDto.amount())<0){
            throw new IllegalArgumentException("The balance is less than the withdrawal amount");
        } else if (walletOperationRequestDto.operationType().equals(OperationType.WITHDRAW)) {
            temp.setBalance(temp.getBalance().subtract(walletOperationRequestDto.amount()));
        } else if(walletOperationRequestDto.operationType().equals(OperationType.DEPOSIT)){
            temp.setBalance(temp.getBalance().add(walletOperationRequestDto.amount()));
        }

        log.info("Transaction successfully! Wallet id - {}",walletOperationRequestDto.walletId());
        walletRepository.save(temp);
    }

    public WalletResponseDto getWalletById(UUID id) {
        var walletEntity = walletRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Wallet not found by id - " + id));
        return new WalletResponseDto(
                id,
                walletEntity.getBalance()
        );
    }
}
    