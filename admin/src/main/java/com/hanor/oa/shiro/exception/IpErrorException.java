package com.hanor.oa.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;

public class IpErrorException extends AuthenticationException {

    public IpErrorException() {
    }

    public IpErrorException(String message) {
        super(message);
    }
}
