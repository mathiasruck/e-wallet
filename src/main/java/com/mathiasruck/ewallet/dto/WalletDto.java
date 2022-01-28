package com.mathiasruck.ewallet.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mathiasruck.ewallet.model.Wallet;

import java.math.BigDecimal;

public class WalletDto {

    private final Wallet wallet;

    public WalletDto() {
        wallet = new Wallet();
    }

    public WalletDto(Wallet wallet) {
        this.wallet = wallet;
    }

    public Long getId() {
        return wallet.getId();
    }

    public void setId(Long id) {
        wallet.setId(id);
    }

    public String getOwnerFullName() {
        return wallet.getOwnerFullName();
    }

    public void setOwnerFullName(String ownerFullName) {
        wallet.setOwnerFullName(ownerFullName);
    }

    public BigDecimal getBalance() {
        return wallet.getBalance();
    }

    public void setBalance(BigDecimal balance) {
        wallet.setBalance(balance);
    }

    @JsonIgnore
    public Wallet get() {
        return wallet;
    }
}
