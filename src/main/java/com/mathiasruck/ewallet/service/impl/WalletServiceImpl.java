package com.mathiasruck.ewallet.service.impl;

import com.mathiasruck.ewallet.dto.WalletDto;
import com.mathiasruck.ewallet.exception.WalletException;
import com.mathiasruck.ewallet.model.Wallet;
import com.mathiasruck.ewallet.repository.WalletRepository;
import com.mathiasruck.ewallet.service.TransactionHistoryService;
import com.mathiasruck.ewallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.mathiasruck.ewallet.util.Messages.WALLET_ID_NOT_FOUND;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Override
    public List<WalletDto> listAll() {
        return StreamSupport.stream(walletRepository.findAll().spliterator(), false)
                .map(WalletDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public WalletDto create(Wallet wallet) {
        Wallet savedWallet = walletRepository.save(wallet);
        return new WalletDto(savedWallet);
    }

    @Override
    public WalletDto update(Long walletId, Wallet wallet) {
        Wallet walletFromDb = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletException(WALLET_ID_NOT_FOUND));
        Wallet walletToSave = wallet.toBuilder()
                .id(walletId)
                .build();
        BigDecimal originalBalance = walletFromDb.getBalance();
        BigDecimal newBalance = walletToSave.getBalance();
        Wallet walletSaved = walletRepository.save(walletToSave);

        if (originalBalance.compareTo(newBalance) < 0) {
            transactionHistoryService.createAddTransaction(walletSaved, originalBalance.subtract(newBalance).abs());
        } else if (originalBalance.compareTo(newBalance) > 0) {
            transactionHistoryService.createWithdrawTransaction(walletSaved, originalBalance.subtract(newBalance).abs());
        }
        return new WalletDto(walletSaved);
    }

    @Override
    public void delete(Long walletId) {
        if (walletRepository.existsById(walletId)) {
            walletRepository.deleteById(walletId);
            return;
        }
        throw new WalletException(WALLET_ID_NOT_FOUND);
    }

    @Override
    public WalletDto getById(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletException(WALLET_ID_NOT_FOUND));
        return new WalletDto(wallet);
    }
}
