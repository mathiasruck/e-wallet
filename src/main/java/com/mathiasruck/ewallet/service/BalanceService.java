package com.mathiasruck.ewallet.service;

import com.mathiasruck.ewallet.dto.BalanceDto;

public interface BalanceService {

    BalanceDto get(Long walletId);

    BalanceDto add(Long walletId, BalanceDto balanceDto);

    BalanceDto withdraw(Long walletId, BalanceDto balanceDto);
}
