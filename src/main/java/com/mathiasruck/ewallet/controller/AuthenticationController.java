package com.mathiasruck.ewallet.controller;

import com.mathiasruck.ewallet.dto.AuthenticationRequestDto;
import com.mathiasruck.ewallet.model.AuthenticationResponse;
import com.mathiasruck.ewallet.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = "/v1/login")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequestDto authenticationRequestDto) throws UsernameNotFoundException {
        AuthenticationResponse response = authenticationService.validateAuthentication(authenticationRequestDto.getAuthenticationRequest());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
}