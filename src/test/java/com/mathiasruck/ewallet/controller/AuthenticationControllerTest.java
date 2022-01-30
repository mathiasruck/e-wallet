package com.mathiasruck.ewallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mathiasruck.ewallet.dto.AuthenticationRequestDto;
import com.mathiasruck.ewallet.exception.WalletException;
import com.mathiasruck.ewallet.model.AuthenticationRequest;
import com.mathiasruck.ewallet.service.AuthenticationService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.mathiasruck.ewallet.util.TestData.getAuthenticationResponseData;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {AuthenticationController.class}, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void shouldAuthenticateSuccessfully() throws Exception {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .username("username")
                .password("password")
                .build();
        String dtoAsJson = new ObjectMapper().writeValueAsString(new AuthenticationRequestDto(authenticationRequest));
        when(authenticationService.validateAuthentication(Mockito.any())).thenReturn(getAuthenticationResponseData());

        this.mockMvc.perform(post("/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.roles.[0]").value("ROLE_ADMIN"));
    }

    @Test
    public void shouldNotAuthenticate() throws Exception {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .username("username")
                .password("password")
                .build();
        String dtoAsJson = new ObjectMapper().writeValueAsString(new AuthenticationRequestDto(authenticationRequest));
        when(authenticationService.validateAuthentication(Mockito.any())).thenThrow(new WalletException("user_not_found"));

        this.mockMvc.perform(post("/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("user_not_found"));
    }
}
