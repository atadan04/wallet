package org.komlev.wallet;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.komlev.wallet.dto.WalletOperationRequestDto;
import org.komlev.wallet.dto.WalletResponseDto;
import org.komlev.wallet.entity.WalletEntity;
import org.komlev.wallet.enums.OperationType;
import org.komlev.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class WalletChangeBalanceIT extends CommonIT {

    private static final BigDecimal INITIAL_BALANCE = new BigDecimal(2000);
    private static final BigDecimal DEPOSIT_AMOUNT = new BigDecimal(1000);
    private static final BigDecimal WITHDRAW_AMOUNT = new BigDecimal(1000);
    private static final UUID ID = UUID.fromString("bc2d0f53-a041-46e7-a14c-267875a49f0c");

    @Autowired
    private WalletRepository walletRepository;

    @Test
    @Sql(statements = {"INSERT INTO wallet (id,balance) values ('bc2d0f53-a041-46e7-a14c-267875a49f0c',2000)"})
    @Sql(statements = {"DELETE FROM wallet WHERE id='bc2d0f53-a041-46e7-a14c-267875a49f0c'"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldDepositSuccessfully() {

        System.out.println("All wallets: " + walletRepository.findAll());
        WalletOperationRequestDto requestDto = new WalletOperationRequestDto(
                ID,
                OperationType.DEPOSIT,
                DEPOSIT_AMOUNT
        );

        ResponseEntity<WalletResponseDto> actual = sendPost("/api/v1/wallet", requestDto, WalletResponseDto.class);

        assertEquals(HttpStatusCode.valueOf(200), actual.getStatusCode());
        assertNotNull(actual.getBody());
        assertEquals(ID.toString(), actual.getBody().id().toString());
        assertTrue(actual.getBody().balance().compareTo(BigDecimal.valueOf(3000)) == 0);
        WalletEntity foundWallet = walletRepository.findById(ID)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found by id: " + ID));
        assertTrue(foundWallet.getBalance().compareTo(BigDecimal.valueOf(3000)) == 0);

    }

    @Test
    @Sql(statements = {"INSERT INTO wallet (id,balance) values ('bc2d0f53-a041-46e7-a14c-267875a49f0c',2000)"})
    @Sql(statements = {"DELETE FROM wallet WHERE id='bc2d0f53-a041-46e7-a14c-267875a49f0c'"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldWithdrawSuccessfully() {
        WalletOperationRequestDto requestDto = new WalletOperationRequestDto(
                ID,
                OperationType.WITHDRAW,
                WITHDRAW_AMOUNT
        );

        ResponseEntity<WalletResponseDto> actual = sendPost("/api/v1/wallet", requestDto, WalletResponseDto.class);

        assertEquals(HttpStatusCode.valueOf(200), actual.getStatusCode());
        assertNotNull(actual.getBody());
        assertEquals(ID.toString(), actual.getBody().id().toString());
        assertTrue(BigDecimal.valueOf(1000).compareTo(actual.getBody().balance()) == 0);

        WalletEntity foundWallet = walletRepository.findById(ID)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found by id: " + ID));
        assertTrue(BigDecimal.valueOf(1000).compareTo(foundWallet.getBalance()) == 0);
    }

    @Test
    @Sql(statements = {"INSERT INTO wallet (id,balance) values ('bc2d0f53-a041-46e7-a14c-267875a49f0c',2000)"})
    @Sql(statements = {"DELETE FROM wallet WHERE id='bc2d0f53-a041-46e7-a14c-267875a49f0c'"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldReturn400WhenWithdrawingMoreThanBalance() {
        WalletOperationRequestDto requestDto = new WalletOperationRequestDto(
                ID,
                OperationType.WITHDRAW,
                BigDecimal.valueOf(5000)
        );

        ResponseEntity<WalletResponseDto> actual = sendPost("/api/v1/wallet", requestDto, WalletResponseDto.class);

        assertEquals(HttpStatusCode.valueOf(400), actual.getStatusCode());
        WalletEntity walletEntity = walletRepository.findById(ID)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found by id: " + ID));
        assertTrue(INITIAL_BALANCE.compareTo(walletEntity.getBalance()) == 0);
    }

    @Test
    @Sql(statements = {"INSERT INTO wallet (id,balance) values ('bc2d0f53-a041-46e7-a14c-267875a49f0c',2000)"})
    @Sql(statements = {"DELETE FROM wallet WHERE id='bc2d0f53-a041-46e7-a14c-267875a49f0c'"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldReturn404WhenWalletNotFound() {
        WalletOperationRequestDto requestDto = new WalletOperationRequestDto(
                UUID.fromString("ab2d0f53-a041-46e7-a14c-267875a49f0c"),//non-existent identifier
                OperationType.WITHDRAW,
                BigDecimal.valueOf(5000)
        );
        ResponseEntity<WalletResponseDto> actual = sendPost("/api/v1/wallet", requestDto, WalletResponseDto.class);

        assertEquals(HttpStatusCode.valueOf(404), actual.getStatusCode());
    }

    @Test
    @Sql(statements = {"INSERT INTO wallet (id,balance) values ('bc2d0f53-a041-46e7-a14c-267875a49f0c',2000)"})
    @Sql(statements = {"DELETE FROM wallet WHERE id='bc2d0f53-a041-46e7-a14c-267875a49f0c'"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldReturn400WhenAmountIsZero() {
        WalletOperationRequestDto requestDto = new WalletOperationRequestDto(
                ID,
                OperationType.WITHDRAW,
                BigDecimal.ZERO //amount is zero
        );
        ResponseEntity<WalletResponseDto> actual = sendPost("/api/v1/wallet", requestDto, WalletResponseDto.class);

        assertEquals(HttpStatusCode.valueOf(400), actual.getStatusCode());
    }

    @Test
    @Sql(statements = {"INSERT INTO wallet (id,balance) values ('bc2d0f53-a041-46e7-a14c-267875a49f0c',2000)"})
    @Sql(statements = {"DELETE FROM wallet WHERE id='bc2d0f53-a041-46e7-a14c-267875a49f0c'"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldReturn400WhenAmountIsNegative() {
        WalletOperationRequestDto requestDto = new WalletOperationRequestDto(
                ID,
                OperationType.WITHDRAW,
                BigDecimal.valueOf(-1000) //amount is negative
        );
        ResponseEntity<WalletResponseDto> actual = sendPost("/api/v1/wallet", requestDto, WalletResponseDto.class);

        assertEquals(HttpStatusCode.valueOf(400), actual.getStatusCode());
    }
}
