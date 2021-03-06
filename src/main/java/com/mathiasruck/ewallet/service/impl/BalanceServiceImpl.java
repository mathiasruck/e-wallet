package com.mathiasruck.ewallet.service.impl;

import com.mathiasruck.ewallet.dto.BalanceDto;
import com.mathiasruck.ewallet.exception.WalletException;
import com.mathiasruck.ewallet.model.Wallet;
import com.mathiasruck.ewallet.repository.WalletRepository;
import com.mathiasruck.ewallet.service.BalanceService;
import com.mathiasruck.ewallet.service.TransactionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import static com.mathiasruck.ewallet.util.Messages.BALANCE_CANNOT_BE_SMALLER_THAN_ZERO;
import static com.mathiasruck.ewallet.util.Messages.WALLET_ID_NOT_FOUND;

@Service
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Override
    public BalanceDto get(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId).
                orElseThrow(() -> new WalletException(WALLET_ID_NOT_FOUND));

        return BalanceDto.builder()
                .balance(wallet.getBalance())
                .build();
    }

    @Override
    public BalanceDto add(Long walletId, BalanceDto balanceDto) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletException(WALLET_ID_NOT_FOUND));

        BigDecimal finalBalance = Optional.of(wallet.getBalance().add(balanceDto.getBalance()))
                .filter(balance -> balance.compareTo(BigDecimal.ZERO) >= 0)
                .orElseThrow(() -> new WalletException(BALANCE_CANNOT_BE_SMALLER_THAN_ZERO));
        wallet.setBalance(finalBalance);

        transactionHistoryService.createAddTransaction(wallet, balanceDto.getBalance());

        Wallet savedWallet = walletRepository.save(wallet);
        return  BalanceDto.builder()
                .balance(savedWallet.getBalance())
                .build();
    }

    @Override
    public BalanceDto withdraw(Long walletId, BalanceDto balanceDto) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletException(WALLET_ID_NOT_FOUND));

        BigDecimal finalBalance = Optional.of(wallet.getBalance().subtract(balanceDto.getBalance()))
                .filter(balance -> balance.compareTo(BigDecimal.ZERO) >= 0)
                .orElseThrow(() -> new WalletException(BALANCE_CANNOT_BE_SMALLER_THAN_ZERO));
        wallet.setBalance(finalBalance);

        transactionHistoryService.createWithdrawTransaction(wallet, balanceDto.getBalance());

        Wallet savedWallet = walletRepository.save(wallet);
        return  BalanceDto.builder()
                .balance(savedWallet.getBalance())
                .build();
    }

}