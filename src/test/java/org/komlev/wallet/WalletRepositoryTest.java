package org.komlev.wallet;

import org.junit.jupiter.api.Test;
import org.komlev.wallet.entity.WalletEntity;
import org.komlev.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class WalletRepositoryTest extends CommonIT{

    private WalletRepository walletRepository;
    @Test
    void shouldSaveAndFindWallet(){
        WalletEntity wallet = new WalletEntity();
        wallet.setId(UUID.randomUUID());
        wallet.setBalance(BigDecimal.ZERO);

        WalletEntity savedEntity = walletRepository.save(wallet);
        Optional<WalletEntity> foundWalletById = walletRepository.findById(savedEntity.getId());

        assertThat(foundWalletById).isPresent();
        assertThat(foundWalletById.get().getBalance()).isZero();

    }
}
