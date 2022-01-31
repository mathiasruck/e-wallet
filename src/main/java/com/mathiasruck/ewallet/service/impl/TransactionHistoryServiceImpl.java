package com.mathiasruck.ewallet.service.impl;

import com.mathiasruck.ewallet.enums.TransactionType;
import com.mathiasruck.ewallet.model.TransactionHistory;
import com.mathiasruck.ewallet.model.Wallet;
import com.mathiasruck.ewallet.repository.TransactionHistoryRepository;
import com.mathiasruck.ewallet.service.TransactionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public void createAddTransaction(Wallet wallet, BigDecimal value) {
        TransactionHistory transactionHistory = TransactionHistory.builder()
                .value(value)
                .finalBalance(wallet.getBalance())
                .executionDate(OffsetDateTime.now())
                .transactionType(TransactionType.ADD)
                .wallet(wallet)
                .build();
        transactionHistoryRepository.save(transactionHistory);
    }

    @Override
    public void createWithdrawTransaction(Wallet wallet, BigDecimal value) {
        TransactionHistory transactionHistory = TransactionHistory.builder()
                .value(value)
                .finalBalance(wallet.getBalance())
                .executionDate(OffsetDateTime.now())
                .transactionType(TransactionType.WITHDRAW)
                .wallet(wallet)
                .build();
        transactionHistoryRepository.save(transactionHistory);
    }

    @Override
    public List<TransactionHistory> getTransactionHistoryByWalletId(Long walletId) {
        return transactionHistoryRepository.findAllByWalletId(walletId);
    }

}
