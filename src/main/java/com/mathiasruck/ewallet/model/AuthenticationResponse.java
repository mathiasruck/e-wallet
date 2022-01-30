package com.mathiasruck.ewallet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 345L;

    private final String jwt;
    private int id;
    private String username;
    private List<String> roles;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

}