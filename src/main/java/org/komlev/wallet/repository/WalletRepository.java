package org.komlev.wallet.repository;

import org.komlev.wallet.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, UUID> {

}
