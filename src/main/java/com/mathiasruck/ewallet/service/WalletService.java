package com.mathiasruck.ewallet.service;


import com.mathiasruck.ewallet.dto.WalletDto;
import com.mathiasruck.ewallet.model.Wallet;

import java.util.List;

public interface WalletService {

    List<WalletDto> listAll();

    WalletDto create(Wallet wallet);

    WalletDto update(Long walletId, Wallet wallet);

    void delete(Long walletId);

    WalletDto getById(Long walletId);

}
