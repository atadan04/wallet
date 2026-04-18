package org.komlev.wallet.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "wallet")
@Data
@RequiredArgsConstructor
public class WalletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "wallet_id",nullable = false,unique = true)
    private UUID id;
    @Column(name="balance",nullable = false)
    private BigDecimal balance;
}
