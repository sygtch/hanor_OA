package com.hanor.oa.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

public class CaptchaInToken extends UsernamePasswordToken {
    //验证码
    private String captchaCode;

    public CaptchaInToken(String username, String password,String host, boolean rememberMe,String captchaCode){

        super(username,password,rememberMe,host);
        this.captchaCode = captchaCode;

    }

    public String getCaptchaCode(){
        return this.captchaCode;
    }
}
