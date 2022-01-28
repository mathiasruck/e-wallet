package com.mathiasruck.ewallet.controller;

import com.mathiasruck.ewallet.dto.WalletDto;
import com.mathiasruck.ewallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping
    public List<WalletDto> listAll() {
        return walletService.listAll();
    }

    @GetMapping("/{walletId}")
    public WalletDto getById(@PathVariable("walletId") Long walletId) {
        return walletService.getById(walletId);
    }

    @DeleteMapping("/{walletId}")
    public void deleteById(@PathVariable("walletId") Long walletId) {
        walletService.delete(walletId);
    }

    @PostMapping
    public WalletDto create(@RequestBody WalletDto wallet) {
        return walletService.create(wallet.get());
    }

    @PutMapping("/{walletId}")
    public WalletDto update(@PathVariable("walletId") Long walletId, @RequestBody WalletDto wallet) {
        return walletService.update(walletId, wallet.get());
    }
}