package com.mathiasruck.ewallet.repository;

import com.mathiasruck.ewallet.model.TransactionHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionHistoryRepository extends CrudRepository<TransactionHistory, Long> {

    List<TransactionHistory> findAllByWalletId(Long walletId);

}
