package com.mathiasruck.ewallet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

}
