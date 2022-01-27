package com.mathiasruck.ewallet.service.impl;

import com.mathiasruck.ewallet.model.AuthenticationUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return AuthenticationUserDetails.builder()
                .id(1)
                .username(checkAndGetUsername(userName))
                .password(new BCryptPasswordEncoder().encode("password"))
                .enabled(true)
                .authorities(Arrays.stream(new String[]{"ROLE_ADMIN"})
                        .map(SimpleGrantedAuthority::new)
                        .collect(toList()))
                .build();
    }

    private String checkAndGetUsername(String userName) {
        return Optional.of(userName).filter("user"::equalsIgnoreCase)
                .orElseThrow(() -> new UsernameNotFoundException(""));
    }
}
