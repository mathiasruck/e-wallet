package com.mathiasruck.ewallet.builder;

import com.mathiasruck.ewallet.model.AuthenticationResponse;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

public class AuthenticationResponseBuilder {

    private static final String JWT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoyMDAwNjc4NzA0LCJpYXQiOjE2MjE5ODc1MDR9.82edJgCTF1HBLPXGjiwsHrjzTu0gNhci1yUOyjKif0o";
    private AuthenticationResponse response;

    private AuthenticationResponseBuilder() {

    }

    public static AuthenticationResponseBuilder getAuthenticationResponse() {
        AuthenticationResponseBuilder builder = new AuthenticationResponseBuilder();
        builder.response = new AuthenticationResponse(JWT_TOKEN);
        return builder;
    }

    public AuthenticationResponseBuilder withId(int id) {
        response.setId(id);
        return this;
    }

    public AuthenticationResponseBuilder withUsername(String username) {
        response.setUsername(username);
        return this;
    }

    public AuthenticationResponseBuilder withRole(String role) {
        response.setRoles(of(role).collect(toList()));
        return this;
    }

    public AuthenticationResponse build() {
        return response;
    }
}
