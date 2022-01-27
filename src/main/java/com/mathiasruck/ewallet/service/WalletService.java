package com.mathiasruck.ewallet.service;


import com.mathiasruck.ewallet.model.Wallet;

import java.util.List;

public interface WalletService {

    List<Wallet> listAll();

    Wallet save(Wallet wallet);

    void delete(Long id);

    Wallet getById(Long id);

}
