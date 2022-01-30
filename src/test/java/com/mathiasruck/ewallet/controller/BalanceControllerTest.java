package com.mathiasruck.ewallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mathiasruck.ewallet.dto.BalanceDto;
import com.mathiasruck.ewallet.service.BalanceService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BalanceController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class BalanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BalanceService service;

    @Test
    public void getByWalletIdShouldReturnCorrectBalance() throws Exception {
        when(service.get(any(Long.class))).thenReturn(BalanceDto.builder().balance(BigDecimal.valueOf(10000)).build());
        this.mockMvc.perform(get("/v1/wallets/1/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(10000));
    }

    @Test
    public void addBalanceSuccessfully() throws Exception {
        BalanceDto balanceDto = BalanceDto.builder().balance(BigDecimal.valueOf(299.33)).build();
        String balanceJson = new ObjectMapper().writeValueAsString(balanceDto);
        when(service.add(any(Long.class), any(BalanceDto.class)))
                .thenReturn(balanceDto);
        this.mockMvc.perform(patch("/v1/wallets/1/balance/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(balanceJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(299.33));
    }

    @Test
    public void withdrawBalanceSuccessfully() throws Exception {
        BalanceDto balanceDto = BalanceDto.builder().balance(BigDecimal.valueOf(432.09)).build();
        String balanceJson = new ObjectMapper().writeValueAsString(balanceDto);
        when(service.withdraw(any(Long.class), any(BalanceDto.class)))
                .thenReturn(balanceDto);
        this.mockMvc.perform(patch("/v1/wallets/1/balance/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(balanceJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(432.09));
    }
}
