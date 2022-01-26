package com.mathiasruck.ewallet.service.impl;

import com.mathiasruck.ewallet.model.Wallet;
import com.mathiasruck.ewallet.repository.WalletRepository;
import com.mathiasruck.ewallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public List<Wallet> listAll() {
        List<Wallet> walletList = new ArrayList<>();
        walletRepository.findAll().forEach(walletList::add);
        return walletList;
    }

    @Override
    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @Override
    public void delete(Long id) {
        walletRepository.deleteById(id);

    }

    @Override
    public Wallet getById(Long id) {
        return walletRepository.findById(id).orElseThrow();
    }

}
