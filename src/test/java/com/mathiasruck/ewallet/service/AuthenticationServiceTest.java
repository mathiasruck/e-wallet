package com.mathiasruck.ewallet.service;

import com.mathiasruck.ewallet.exception.WalletException;
import com.mathiasruck.ewallet.model.AuthenticationRequest;
import com.mathiasruck.ewallet.model.AuthenticationResponse;
import com.mathiasruck.ewallet.model.AuthenticationUserDetails;
import com.mathiasruck.ewallet.service.impl.AuthenticationServiceImpl;
import com.mathiasruck.ewallet.util.JwtUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

import static com.mathiasruck.ewallet.util.TestData.getAuthenticationUserDetails;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class AuthenticationServiceTest {

    AuthenticationService authenticationService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtil jwtTokenUtil;
    @Mock
    private UserDetailsService userDetailsService;
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setup() {
        authenticationService = new AuthenticationServiceImpl();
        setField(authenticationService, "authenticationManager", authenticationManager);
        setField(authenticationService, "jwtTokenUtil", jwtTokenUtil);
        setField(authenticationService, "userDetailsService", userDetailsService);
    }

    @Test
    public void userNotFoundException() {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .username("usernameError")
                .password("passwordError")
                .build();

        WalletException exception= Assertions.assertThrows(
                WalletException.class,
                () -> authenticationService.validateAuthentication(authenticationRequest),
                "Expected authenticationService.validateAuthentication to throw WalletException, but it didn't"
        );

        assertThat(exception.getMessage(), is(equalTo("user_not_found")));
    }

    @Test
    public void successfullyGenerateToken() {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .username("username")
                .password("password")
                .build();

        AuthenticationUserDetails authenticationUserDetails = getAuthenticationUserDetails();
        when(userDetailsService.loadUserByUsername(Mockito.anyString())).thenReturn(authenticationUserDetails);

        when(jwtTokenUtil.generateToken(Mockito.any(AuthenticationUserDetails.class))).thenCallRealMethod();
        AuthenticationResponse response = authenticationService.validateAuthentication(authenticationRequest);
        assertThat(response.getId(), is(equalTo(1)));
        assertThat(response.getUsername(), is(equalTo("user")));
        assertThat(response.getRoles().get(0), is(equalTo("ROLE_ADMIN")));
    }
}
