package com.mathiasruck.ewallet.exception;

public class WalletException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    public WalletException() {
        super();
    }

    public WalletException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}