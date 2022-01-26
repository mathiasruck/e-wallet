package com.mathiasruck.ewallet.repository;

import com.mathiasruck.ewallet.model.Wallet;
import org.springframework.data.repository.CrudRepository;

public interface WalletRepository extends CrudRepository<Wallet, Long> {

}
