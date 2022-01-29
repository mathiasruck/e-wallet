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
    public TransactionHistory createAddTransaction(Wallet wallet, BigDecimal value) {
        return TransactionHistory.builder()
                .value(value)
                .finalBalance(wallet.getBalance())
                .executionDate(OffsetDateTime.now())
                .transactionType(TransactionType.ADD)
                .build();
    }

    @Override
    public TransactionHistory createWithdrawTransaction(Wallet wallet, BigDecimal value) {
        return TransactionHistory.builder()
                .value(value)
                .finalBalance(wallet.getBalance())
                .executionDate(OffsetDateTime.now())
                .transactionType(TransactionType.WITHDRAW)
                .build();
    }

    @Override
    public List<TransactionHistory> getTransactionHistoryByWalletId(Long walletId) {
        return transactionHistoryRepository.findAllByWalletId(walletId);
    }

}
