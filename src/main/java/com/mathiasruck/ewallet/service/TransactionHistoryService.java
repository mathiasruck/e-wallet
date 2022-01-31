package com.mathiasruck.ewallet.service;

import com.mathiasruck.ewallet.model.TransactionHistory;
import com.mathiasruck.ewallet.model.Wallet;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionHistoryService {

    void createAddTransaction(Wallet wallet, BigDecimal value);

    void createWithdrawTransaction(Wallet wallet, BigDecimal value);

    List<TransactionHistory> getTransactionHistoryByWalletId(Long walletId);

}
