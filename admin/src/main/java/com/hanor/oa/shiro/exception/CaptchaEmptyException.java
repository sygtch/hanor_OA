package com.hanor.oa.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;

public class CaptchaEmptyException extends AuthenticationException {

    public CaptchaEmptyException(){}

    public CaptchaEmptyException(String message){
        super(message);
    }

}
