package com.mathiasruck.ewallet.service;


import com.mathiasruck.ewallet.dto.BalanceDto;
import com.mathiasruck.ewallet.model.Wallet;
import com.mathiasruck.ewallet.repository.WalletRepository;
import com.mathiasruck.ewallet.service.impl.BalanceServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.math.BigDecimal;
import java.util.Optional;

import static com.mathiasruck.ewallet.util.TestData.getLukeSkywalkerWallet;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class BalanceServiceTest {

    private BalanceService balanceService;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionHistoryService transactionHistoryService;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setup() {
        balanceService = new BalanceServiceImpl();
        setField(balanceService, "walletRepository", walletRepository);
        setField(balanceService, "transactionHistoryService", transactionHistoryService);
    }

    @Test
    public void getBalanceSuccessfully() {
        when(walletRepository.findById(1L)).thenReturn(Optional.of(getLukeSkywalkerWallet()));
        BalanceDto balanceDto = balanceService.get(1L);

        assertThat(balanceDto.getBalance(), is(equalTo(BigDecimal.valueOf(10000))));
    }

    @Test
    public void addBalanceSuccessfully() {
        Wallet lukeSkywalkerWallet = getLukeSkywalkerWallet();

        when(walletRepository.findById(1L)).thenReturn(Optional.of(lukeSkywalkerWallet));
        when(walletRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        BalanceDto balanceDto = balanceService.add(1L, BalanceDto.builder().balance(BigDecimal.valueOf(30.33)).build());

        assertThat(balanceDto.getBalance(), is(equalTo(BigDecimal.valueOf(10030.33))));
        verify(walletRepository, times(1)).findById(1L);
        verify(walletRepository, times(1)).save(lukeSkywalkerWallet);
    }

    @Test
    public void withdrawBalanceSuccessfully() {
        Wallet lukeSkywalkerWallet = getLukeSkywalkerWallet();

        when(walletRepository.findById(1L)).thenReturn(Optional.of(lukeSkywalkerWallet));
        when(walletRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        BalanceDto balanceDto = balanceService.withdraw(1L, BalanceDto.builder().balance(BigDecimal.valueOf(30.33)).build());

        assertThat(balanceDto.getBalance(), is(equalTo(BigDecimal.valueOf(9969.67))));
        verify(walletRepository, times(1)).findById(1L);
        verify(walletRepository, times(1)).save(lukeSkywalkerWallet);
    }
}
