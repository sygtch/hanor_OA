package com.hanor.oa.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CaptchaInToken extends UsernamePasswordToken {
    /**
     * 验证码
     */
    private String captchaCode;

    /**
     * 请求ip
     */
    private String requestIP;

    public  CaptchaInToken(){}

    public CaptchaInToken(String username, String password, boolean rememberMe, String host, String captchaCode, String requestIP) {
        super(username, password, rememberMe, host);
        this.captchaCode = captchaCode;
        this.requestIP = requestIP;
    }

    public String getCaptchaCode(){
        return this.captchaCode;
    }

    public String getRequestIP(){
        return this.requestIP;
    }
}
