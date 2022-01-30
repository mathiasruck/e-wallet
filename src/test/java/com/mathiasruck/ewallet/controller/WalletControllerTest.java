package com.mathiasruck.ewallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mathiasruck.ewallet.dto.WalletDto;
import com.mathiasruck.ewallet.model.Wallet;
import com.mathiasruck.ewallet.service.WalletService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static com.mathiasruck.ewallet.util.TestData.getC3poWallet;
import static com.mathiasruck.ewallet.util.TestData.getLukeSkywalkerWallet;
import static java.util.List.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WalletController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService service;

    @Test
    public void listAllShouldReturnAllAvailableWallets() throws Exception {
        when(service.listAll()).thenReturn(of(new WalletDto(getLukeSkywalkerWallet()), new WalletDto(getC3poWallet())));
        this.mockMvc.perform(get("/v1/wallets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].ownerFullName").value("Luke Skywalker"))
                .andExpect(jsonPath("$.[0].balance").value(10000))
                .andExpect(jsonPath("$.[1].id").value(2L))
                .andExpect(jsonPath("$.[1].ownerFullName").value("C-3PO"))
                .andExpect(jsonPath("$.[1].balance").value(100));
    }

    @Test
    public void getByWalletIdShouldReturnCorrectWallet() throws Exception {
        Wallet lukeSkywalker = getLukeSkywalkerWallet().toBuilder()
                .id(2L)
                .build();
        when(service.getById(any(Long.class))).thenReturn(new WalletDto(lukeSkywalker));
        this.mockMvc.perform(get("/v1/wallets/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.ownerFullName").value("Luke Skywalker"))
                .andExpect(jsonPath("$.balance").value(10000));
    }

    @Test
    public void createNewWalletShouldReturnCorrectly() throws Exception {
        Wallet lukeSkywalkerToSend = getLukeSkywalkerWallet().toBuilder()
                .id(null)
                .build();
        Wallet lukeSkywalkerSaved = getLukeSkywalkerWallet();

        String walletJson = new ObjectMapper().writeValueAsString(lukeSkywalkerToSend);
        when(service.create(any(Wallet.class))).thenReturn(new WalletDto(lukeSkywalkerSaved));
        this.mockMvc.perform(post("/v1/wallets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(walletJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.ownerFullName").value("Luke Skywalker"))
                .andExpect(jsonPath("$.balance").value(10000));
    }

    @Test
    public void deleteWalletSuccessfully() throws Exception {
        this.mockMvc.perform(delete("/v1/wallets/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateWalletSuccessfully() throws Exception {
        Wallet lukeSkywalkerSaved = getLukeSkywalkerWallet().toBuilder()
                .balance(BigDecimal.valueOf(7900))
                .build();

        String walletJson = new ObjectMapper().writeValueAsString(getLukeSkywalkerWallet());
        when(service.update(any(Long.class), any(Wallet.class))).thenReturn(new WalletDto(lukeSkywalkerSaved));
        this.mockMvc.perform(put("/v1/wallets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(walletJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.ownerFullName").value("Luke Skywalker"))
                .andExpect(jsonPath("$.balance").value(7900));
    }
}
