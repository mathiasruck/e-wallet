package com.mathiasruck.ewallet.service;

import com.mathiasruck.ewallet.model.AuthenticationRequest;
import com.mathiasruck.ewallet.model.AuthenticationResponse;

public interface AuthenticationService {

    public AuthenticationResponse validateAuthentication(AuthenticationRequest authenticationRequest);

}
