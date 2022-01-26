package com.mathiasruck.ewallet.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.mathiasruck.ewallet.builder.AuthenticationUserDetailsBuilder.getAuthenticationUserDetails;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return getAuthenticationUserDetails().withDefaultData().build();
    }

}
