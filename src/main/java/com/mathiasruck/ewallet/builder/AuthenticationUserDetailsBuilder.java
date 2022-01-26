package com.mathiasruck.ewallet.builder;

import com.mathiasruck.ewallet.model.AuthenticationUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

import static java.util.stream.Collectors.toList;

public class AuthenticationUserDetailsBuilder {
    private AuthenticationUserDetails authUser;

    public static AuthenticationUserDetailsBuilder getAuthenticationUserDetails() {
        AuthenticationUserDetailsBuilder builder = new AuthenticationUserDetailsBuilder();
        builder.authUser = new AuthenticationUserDetails();
        return builder;
    }

    public AuthenticationUserDetailsBuilder withDefaultData() {
        authUser.setId(1);
        authUser.setUsername("user");
        authUser.setPassword(new BCryptPasswordEncoder().encode("password"));
        authUser.setEnabled(true);
        authUser.setAuthorities(Arrays.stream(new String[] { "ROLE_ADMIN" })
                .map(SimpleGrantedAuthority::new)
                .collect(toList()));
        return this;
    }

    public AuthenticationUserDetails build() {
        return authUser;
    }
}
