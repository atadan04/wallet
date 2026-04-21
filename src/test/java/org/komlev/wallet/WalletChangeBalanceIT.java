package org.komlev.wallet;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.komlev.wallet.entity.WalletEntity;
import org.komlev.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WalletChangeBalanceIT extends CommonIT {

    private static final UUID ID = UUID.fromString("bc2d0f53-a041-46e7-a14c-267875a49f0c");

    @Autowired
    private WalletRepository walletRepository;

    @Test
    @Sql(statements = {"INSERT INTO wallet (id,balance) values ('bc2d0f53-a041-46e7-a14c-267875a49f0c',0)"})
    void shouldSaveAndFindWallet() {
        WalletEntity wallet = new WalletEntity();
        wallet.setId(ID);
        wallet.setBalance(BigDecimal.valueOf(10));
        //переделать на walletOperation
        ResponseEntity<WalletEntity> actual = sendPost("/api/v1/wallet", wallet, WalletEntity.class);

        assertEquals(HttpStatusCode.valueOf(200), actual.getStatusCode());
        assertNotNull(actual.getBody());
        assertEquals(ID.toString(), actual.getBody().getId().toString());
        assertEquals(BigDecimal.valueOf(10), actual.getBody().getBalance());
        WalletEntity foundWallet = walletRepository.findById(ID)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        assertEquals(BigDecimal.valueOf(10),foundWallet.getBalance());

    }
}
