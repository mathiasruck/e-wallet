package com.mathiasruck.ewallet.builder;

import com.mathiasruck.ewallet.model.Wallet;

import java.time.LocalDateTime;

public class WalletBuilder {

    Wallet wallet;

    private WalletBuilder() {
    }

    public static WalletBuilder getWallet() {
        WalletBuilder builder = new WalletBuilder();
        builder.wallet = new Wallet();
        return builder;
    }

    public WalletBuilder withPrice(Double price) {
        wallet.setBalance(price);
        return this;

    }

    public WalletBuilder withRandonCreationDate() {
        wallet.setCreationDate(LocalDateTime.now());
        return this;
    }

    public WalletBuilder withName(String name) {
        wallet.setOwnerFullName(name);
        return this;
    }

    public WalletBuilder withId(Long id) {
        wallet.setId(id);
        return this;
    }

    public Wallet build() {
        return wallet;
    }
}
