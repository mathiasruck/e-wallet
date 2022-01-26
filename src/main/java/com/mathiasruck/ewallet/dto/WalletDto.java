package com.mathiasruck.ewallet.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mathiasruck.ewallet.model.Wallet;

import java.time.LocalDateTime;

public class WalletDto {
    private Wallet wallet;

    public WalletDto() {
        wallet = new Wallet();
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

    public Double getBalance() {
        return wallet.getBalance();
    }

    public void setBalance(Double balance) {
        wallet.setBalance(balance);
    }

    public LocalDateTime getCreationDate() {
        return wallet.getCreationDate();
    }

    @JsonIgnore
    public Wallet get() {
        return wallet;
    }
}
