package com.mathiasruck.ewallet.service;

import com.mathiasruck.ewallet.service.impl.UserDetailsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserDetailsServiceTest {

    private static final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
    private UserDetailsService userDetailsService;

    @Before
    public void setup() {
        userDetailsService = new UserDetailsServiceImpl();
    }

    @Test
    public void successfullyLoadUser() {
        UserDetails username = userDetailsService.loadUserByUsername("user");
        GrantedAuthority authority = username.getAuthorities().stream().findFirst().get();
        assertThat(authority.getAuthority(), is(equalTo("ROLE_ADMIN")));
        assertThat(username.isEnabled(), is(true));
        assertThat(username.getUsername(), is(equalTo("user")));
        assertThat(BCRYPT_PATTERN.matcher(username.getPassword()).matches(), is(true));
    }
}
