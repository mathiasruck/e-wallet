package com.mathiasruck.ewallet.service;


import com.mathiasruck.ewallet.model.TransactionHistory;
import com.mathiasruck.ewallet.repository.TransactionHistoryRepository;
import com.mathiasruck.ewallet.service.impl.TransactionHistoryServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.math.BigDecimal;
import java.util.List;

import static com.mathiasruck.ewallet.util.TestData.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class TransactionHistoryServiceTest {

    private TransactionHistoryService service;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private TransactionHistoryRepository transactionHistoryRepository;

    @Before
    public void setup() {
        service = new TransactionHistoryServiceImpl();
        setField(service, "transactionHistoryRepository", transactionHistoryRepository);
    }

    @Test
    public void listAllTransactionsByWalletsSuccessfully() {
        when(transactionHistoryRepository.findAllByWalletId(any())).thenReturn(List.of(getTransactionAdd(), getTransactionWithdraw()));
        List<TransactionHistory> transactionHistoryByWalletId = service.getTransactionHistoryByWalletId(any());

        assertThat(transactionHistoryByWalletId.size(), is(equalTo(2)));
        verify(transactionHistoryRepository, times(1)).findAllByWalletId(any());
    }

    @Test
    public void createWithdrawTransactionSuccessfully() {
        when(transactionHistoryRepository.findAllByWalletId(any())).thenReturn(List.of(getTransactionAdd(), getTransactionWithdraw()));
        service.createWithdrawTransaction(getLukeSkywalkerWallet(), BigDecimal.valueOf(3000));

        verify(transactionHistoryRepository, times(1)).save(any());
    }

    @Test
    public void createAddTransactionSuccessfully() {
        when(transactionHistoryRepository.findAllByWalletId(any())).thenReturn(List.of(getTransactionAdd(), getTransactionWithdraw()));
        service.createAddTransaction(getLukeSkywalkerWallet(), BigDecimal.valueOf(3000));

        verify(transactionHistoryRepository, times(1)).save(any());
    }

}
