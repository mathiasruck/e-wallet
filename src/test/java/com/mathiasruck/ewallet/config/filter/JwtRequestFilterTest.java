package com.mathiasruck.ewallet.config.filter;

import com.mathiasruck.ewallet.model.AuthenticationUserDetails;
import com.mathiasruck.ewallet.util.JwtUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.ServletException;
import java.io.IOException;

import static com.mathiasruck.ewallet.util.TestData.getAuthenticationUserDetails;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class JwtRequestFilterTest {

    private static final String JWT_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoyMDAwNjc4NzA0LCJpYXQiOjE2MjE5ODc1MDR9.82edJgCTF1HBLPXGjiwsHrjzTu0gNhci1yUOyjKif0o";

    private JwtRequestFilter jwtRequestFilter;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setup() {
        jwtRequestFilter = new JwtRequestFilter();
        setField(jwtRequestFilter, "userDetailsService", userDetailsService);
        setField(jwtRequestFilter, "jwtUtil", jwtUtil);

    }

    @Test
    public void successfullyFilter() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("local.host");
        request.setRequestURI("/v1/products");
        request.addHeader("Authorization", JWT_TOKEN);
        request.setMethod("OPTIONS");
        when(jwtUtil.extractUsername(Mockito.anyString())).thenReturn("username");
        when(jwtUtil.validateToken(Mockito.anyString(), Mockito.any(UserDetails.class))).thenReturn(true);

        AuthenticationUserDetails authenticationUserDetails = getAuthenticationUserDetails();
        when(userDetailsService.loadUserByUsername(Mockito.anyString())).thenReturn(authenticationUserDetails);
        jwtRequestFilter.doFilterInternal(request, null, null);
    }

}
