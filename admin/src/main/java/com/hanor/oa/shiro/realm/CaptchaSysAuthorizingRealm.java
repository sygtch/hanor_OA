package com.hanor.oa.shiro.realm;

import com.hanor.oa.shiro.exception.CaptchaEmptyException;
import com.hanor.oa.shiro.exception.CaptchaErrorException;
import com.hanor.oa.shiro.token.CaptchaInToken;
import com.wangzc.mvc.shiro.realm.SysAuthorizingRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;

public class CaptchaSysAuthorizingRealm extends SysAuthorizingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //该处token为自定义filter的自定义token，可以强转
        CaptchaInToken captchaInToken = (CaptchaInToken) token;
        String captcha = captchaInToken.getCaptchaCode();

        if (null == captcha || "".equals(captcha.trim())){
            throw new CaptchaEmptyException("验证码不能为空");
        }

        //session中取出正确的验证码
        String sessionCaptcha = (String) SecurityUtils.getSubject().getSession().getAttribute("captcha");
        if (!sessionCaptcha.equals(captcha)){
            throw new CaptchaErrorException("验证码xxxx错误");
        }

        return super.doGetAuthenticationInfo(token);
    }

}
