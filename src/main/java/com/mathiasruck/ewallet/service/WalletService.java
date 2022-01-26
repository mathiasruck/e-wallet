package com.mathiasruck.ewallet.service;


import com.mathiasruck.ewallet.model.Wallet;

import java.util.List;

public interface WalletService {

    public List<Wallet> listAll();

    public Wallet save(Wallet wallet);

    public void delete(Long id);

    public Wallet getById(Long id);

}
