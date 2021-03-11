package com.gruppo13.AuthenticationMS.exception;

import org.springframework.security.core.AuthenticationException;

public class OAuth2AuthProcessingExc extends AuthenticationException {
    private static final long serialVersionUID = 3392450042101522832L;

    public OAuth2AuthProcessingExc(String msg, Throwable t) {
        super(msg, t);
    }

    public OAuth2AuthProcessingExc(String msg) {
        super(msg);
    }
}