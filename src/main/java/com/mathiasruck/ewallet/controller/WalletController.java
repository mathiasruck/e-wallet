package com.mathiasruck.ewallet.controller;

import com.mathiasruck.ewallet.dto.WalletDto;
import com.mathiasruck.ewallet.model.Wallet;
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
    public List<Wallet> listAll() {
        return walletService.listAll();
    }

    @GetMapping("/{id}")
    public Wallet getById(@PathVariable("id") Long id) {
        return walletService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        walletService.delete(id);
    }

    @PostMapping
    public Wallet create(@RequestBody WalletDto wallet) {
        return walletService.save(wallet.get());
    }

    @PutMapping
    public Wallet update(@RequestBody WalletDto wallet) {
        return walletService.save(wallet.get());
    }
}