package com.mathiasruck.ewallet.controller;

import com.mathiasruck.ewallet.dto.BalanceDto;
import com.mathiasruck.ewallet.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @GetMapping("/wallets/{walletId}/balance")
    public BalanceDto get(@PathVariable("walletId") Long walletId) {
        return balanceService.get(walletId);
    }

    @PatchMapping("/wallets/{walletId}/balance/add")
    public BalanceDto add(@PathVariable("walletId") Long walletId, @RequestBody BalanceDto balanceDto) {
        return balanceService.add(walletId, balanceDto);
    }

    @PatchMapping("/wallets/{walletId}/balance/withdraw")
    public BalanceDto withdraw(@PathVariable("walletId") Long walletId, @RequestBody BalanceDto balanceDto) {
        return balanceService.withdraw(walletId, balanceDto);
    }
}