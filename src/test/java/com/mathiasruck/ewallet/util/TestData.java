package com.mathiasruck.ewallet.util;

import com.mathiasruck.ewallet.enums.TransactionType;
import com.mathiasruck.ewallet.model.AuthenticationResponse;
import com.mathiasruck.ewallet.model.AuthenticationUserDetails;
import com.mathiasruck.ewallet.model.TransactionHistory;
import com.mathiasruck.ewallet.model.Wallet;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class TestData {

    public static AuthenticationUserDetails getAuthenticationUserDetails() {
        return AuthenticationUserDetails.builder()
                .id(1)
                .username("user")
                .password(new BCryptPasswordEncoder().encode("password"))
                .enabled(true)
                .authorities(Arrays.stream(new String[]{"ROLE_ADMIN"})
                        .map(SimpleGrantedAuthority::new)
                        .collect(toList())).build();
    }

    public static AuthenticationResponse getAuthenticationResponseData(){
        return AuthenticationResponse.builder()
                .id(1)
                .username("user")
                .roles(List.of("ROLE_ADMIN"))
                .build();
    }

    public static Wallet getLukeSkywalkerWallet(){
        return Wallet.builder()
                .id(1L)
                .ownerFullName("Luke Skywalker")
                .balance(BigDecimal.valueOf(10000))
                .build();
    }

    public static Wallet getC3poWallet(){
        return Wallet.builder()
                .id(2L)
                .ownerFullName("C-3PO")
                .balance(BigDecimal.valueOf(100))
                .build();
    }

    public static TransactionHistory getTransactionAdd(){
        return TransactionHistory.builder()
                .transactionType(TransactionType.ADD)
                .wallet(getLukeSkywalkerWallet())
                .executionDate(OffsetDateTime.now())
                .finalBalance(BigDecimal.valueOf(5000))
                .value(BigDecimal.valueOf(2500))
                .build();
    }

    public static TransactionHistory getTransactionWithdraw(){
        return TransactionHistory.builder()
                .transactionType(TransactionType.WITHDRAW)
                .wallet(getLukeSkywalkerWallet())
                .executionDate(OffsetDateTime.now())
                .finalBalance(BigDecimal.valueOf(2500))
                .value(BigDecimal.valueOf(5000))
                .build();
    }
}
