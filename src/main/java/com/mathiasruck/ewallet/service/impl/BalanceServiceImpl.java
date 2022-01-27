package com.mathiasruck.ewallet.service.impl;

import com.mathiasruck.ewallet.dto.BalanceDto;
import com.mathiasruck.ewallet.exception.WalletException;
import com.mathiasruck.ewallet.model.Wallet;
import com.mathiasruck.ewallet.repository.WalletRepository;
import com.mathiasruck.ewallet.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BalanceServiceImpl implements BalanceService {

    public static final String WALLET_ID_NOT_FOUND = "wallet_id_not_found";
    @Autowired
    private WalletRepository walletRepository;

    @Override
    public BalanceDto get(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId).
                orElseThrow(() -> new WalletException(WALLET_ID_NOT_FOUND));
        return BalanceDto.builder()
                .value(wallet.getBalance())
                .build();
    }

    @Override
    public Wallet add(Long walletId, BalanceDto balanceDto) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletException(WALLET_ID_NOT_FOUND));
        double finalBalance = Optional.of(wallet.getBalance() + balanceDto.getValue()).filter(balance -> balance > 0)
                .orElseThrow(() -> new WalletException("balance_cannot_be_smaller_than_zero"));
        wallet.setBalance(finalBalance);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet withdraw(Long walletId, BalanceDto balanceDto) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletException(WALLET_ID_NOT_FOUND));
        double finalBalance = Optional.of(wallet.getBalance() - balanceDto.getValue()).filter(balance -> balance > 0)
                .orElseThrow(() -> new WalletException("balance_cannot_be_smaller_than_zero"));
        wallet.setBalance(finalBalance);
        return walletRepository.save(wallet);
    }
}