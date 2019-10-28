package com.hanor.oa.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;

public class CaptchaErrorException extends AuthenticationException {

    public CaptchaErrorException(){}

    public CaptchaErrorException(String message){
        super(message);
    }
}
