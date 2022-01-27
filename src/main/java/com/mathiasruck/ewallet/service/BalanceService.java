package com.mathiasruck.ewallet.service;

import com.mathiasruck.ewallet.dto.BalanceDto;
import com.mathiasruck.ewallet.model.Wallet;

public interface BalanceService {

    BalanceDto get(Long walletId);

    Wallet add(Long walletId, BalanceDto balanceDto);

    Wallet withdraw(Long walletId, BalanceDto balanceDto);
}
