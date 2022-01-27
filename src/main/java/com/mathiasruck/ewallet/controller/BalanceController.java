package com.mathiasruck.ewallet.controller;

import com.mathiasruck.ewallet.dto.BalanceDto;
import com.mathiasruck.ewallet.model.Wallet;
import com.mathiasruck.ewallet.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @GetMapping("/wallets/{walletId}/balance")
    public Wallet get(@PathVariable("walletId") Long walletId, @RequestBody BalanceDto balanceDto) {
        return balanceService.add(walletId, balanceDto);
    }

    @PutMapping("/wallets/{walletId}/balance/add")
    public Wallet add(@PathVariable("walletId") Long walletId, @RequestBody BalanceDto balanceDto) {
        return balanceService.add(walletId, balanceDto);
    }

    @PutMapping("/wallets/{walletId}/balance/withdraw")
    public Wallet withdraw(@PathVariable("walletId") Long walletId, @RequestBody BalanceDto balanceDto) {
        return balanceService.withdraw(walletId, balanceDto);
    }

}
