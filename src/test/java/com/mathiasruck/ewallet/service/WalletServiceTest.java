package com.mathiasruck.ewallet.service;


import com.mathiasruck.ewallet.dto.WalletDto;
import com.mathiasruck.ewallet.exception.WalletException;
import com.mathiasruck.ewallet.model.Wallet;
import com.mathiasruck.ewallet.repository.WalletRepository;
import com.mathiasruck.ewallet.service.impl.WalletServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mathiasruck.ewallet.util.TestData.getC3poWallet;
import static com.mathiasruck.ewallet.util.TestData.getLukeSkywalkerWallet;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class WalletServiceTest {

    private WalletService walletService;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionHistoryService transactionHistoryService;

    @Before
    public void setup() {
        walletService = new WalletServiceImpl();
        setField(walletService, "walletRepository", walletRepository);
        setField(walletService, "transactionHistoryService", transactionHistoryService);
    }

    @Test
    public void listAllWalletsSuccessfully() {
        when(walletRepository.findAll()).thenReturn(List.of(getLukeSkywalkerWallet(), getC3poWallet()));
        List<WalletDto> walletList = walletService.listAll();

        assertThat(walletList.size(), is(equalTo(2)));
    }

    @Test
    public void listAllWalletsReturnsZeroItems() {

        when(walletRepository.findAll()).thenReturn(new ArrayList<>());
        List<WalletDto> walletList = walletService.listAll();

        assertThat(walletList.size(), is(equalTo(0)));
        verify(walletRepository, times(1)).findAll();
    }

    @Test
    public void updateWalletSuccessfully() {
        Wallet lukeSkywalkerWallet = getLukeSkywalkerWallet();
        when(walletRepository.findById(any())).thenReturn(Optional.of(lukeSkywalkerWallet));
        when(walletRepository.save(any(Wallet.class))).thenReturn(lukeSkywalkerWallet);
        walletService.update(lukeSkywalkerWallet.getId(), lukeSkywalkerWallet);

        verify(walletRepository, times(1)).save(any());
        verify(transactionHistoryService, times(0)).createWithdrawTransaction(any(), any());
    }

    @Test
    public void updateWalletAndAddFundsSuccessfully() {
        Wallet lukeSkywalkerWalletFromDb = getLukeSkywalkerWallet();
        Wallet lukeSkywalkerWalletToSave = getLukeSkywalkerWallet().toBuilder().balance(BigDecimal.valueOf(15000)).build();

        when(walletRepository.findById(any())).thenReturn(Optional.of(lukeSkywalkerWalletFromDb));
        when(walletRepository.save(any(Wallet.class))).thenReturn(lukeSkywalkerWalletToSave);
        Wallet walletSaved = walletService.update(lukeSkywalkerWalletToSave.getId(), lukeSkywalkerWalletToSave).get();

        assertThat(walletSaved.getBalance(), is(equalTo(BigDecimal.valueOf(15000))));
        verify(walletRepository, times(1)).save(any());
        verify(transactionHistoryService, times(1)).createAddTransaction(any(), any());
        verify(transactionHistoryService, times(0)).createWithdrawTransaction(any(), any());

    }

    @Test
    public void updateWalletAndWithdrawFundsSuccessfully() {
        Wallet lukeSkywalkerWalletFromDb = getLukeSkywalkerWallet();
        Wallet lukeSkywalkerWalletToSave = getLukeSkywalkerWallet().toBuilder().balance(BigDecimal.valueOf(5000)).build();

        when(walletRepository.findById(any())).thenReturn(Optional.of(lukeSkywalkerWalletFromDb));
        when(walletRepository.save(any(Wallet.class))).thenReturn(lukeSkywalkerWalletToSave);
        Wallet walletSaved = walletService.update(lukeSkywalkerWalletToSave.getId(), lukeSkywalkerWalletToSave).get();

        assertThat(walletSaved.getBalance(), is(equalTo(BigDecimal.valueOf(5000))));
        verify(walletRepository, times(1)).save(any());
        verify(transactionHistoryService, times(0)).createAddTransaction(any(), any());
        verify(transactionHistoryService, times(1)).createWithdrawTransaction(any(), any());
    }

    @Test
    public void saveNewWalletSuccessfully() {
        Wallet lukeSkywalkerWallet = getLukeSkywalkerWallet();
        when(walletRepository.save(any(Wallet.class))).thenReturn(lukeSkywalkerWallet);

        WalletDto walletCreated = walletService.create(lukeSkywalkerWallet);
        assertThat(walletCreated.getOwnerFullName(), is(equalTo(lukeSkywalkerWallet.getOwnerFullName())));
        assertThat(walletCreated.getBalance(), is(equalTo(lukeSkywalkerWallet.getBalance())));
        verify(walletRepository, times(1)).save(lukeSkywalkerWallet);
    }

    @Test
    public void deleteWalletSuccessfully() {
        when(walletRepository.existsById(any())).thenReturn(true);
        walletService.delete(15L);

        verify(walletRepository, times(1)).deleteById(15L);
    }

    @Test
    public void getWalletSuccessFully() {
        Wallet lukeSkywalkerWallet = getLukeSkywalkerWallet();

        when(walletRepository.findById(any())).thenReturn(Optional.of(lukeSkywalkerWallet));
        WalletDto walletDto = walletService.getById(10L);
        assertThat(lukeSkywalkerWallet.getId(), is(equalTo(walletDto.getId())));
        assertThat(lukeSkywalkerWallet.getOwnerFullName(), is(equalTo(walletDto.getOwnerFullName())));
        assertThat(lukeSkywalkerWallet.getBalance(), is(equalTo(walletDto.getBalance())));
        verify(walletRepository, times(1)).findById(any());

    }

    @Test(expected = WalletException.class)
    public void getWithInvalidWalletId() {
        walletService.getById(10L);

    }
}
