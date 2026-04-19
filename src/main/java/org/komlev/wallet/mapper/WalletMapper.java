package org.komlev.wallet.mapper;

import org.komlev.wallet.dto.WalletResponseDto;
import org.komlev.wallet.entity.WalletEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface WalletMapper{
    WalletResponseDto toResponseDto(WalletEntity entity);
}
