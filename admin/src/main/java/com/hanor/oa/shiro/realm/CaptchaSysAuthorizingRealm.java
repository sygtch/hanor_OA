package com.hanor.oa.shiro.realm;

import com.hanor.entity.WhiteList;
import com.hanor.oa.shiro.exception.CaptchaEmptyException;
import com.hanor.oa.shiro.exception.CaptchaErrorException;
import com.hanor.oa.shiro.exception.IpErrorException;
import com.hanor.oa.shiro.token.CaptchaInToken;
import com.hanor.oa.util.StringUtils;
import com.wangzc.mvc.entity.SysUser;
import com.wangzc.mvc.shiro.realm.SysAuthorizingRealm;
import com.wangzc.mvc.utils.SysUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;

public class CaptchaSysAuthorizingRealm extends SysAuthorizingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        boolean flag = false;
        //该处token为自定义filter的自定义token，可以强转
        CaptchaInToken captchaInToken = (CaptchaInToken) token;
        String captcha = captchaInToken.getCaptchaCode();
        String requestIP = captchaInToken.getRequestIP();

        //ip验证
        if (StringUtils.isEmpty(requestIP)){
            throw new IpErrorException("无ip请求");
        }
        if (StringUtils.isEmpty(captchaInToken.getUsername())){
            throw new IpErrorException("非法用户");
        }

        Dao dao = SysUtils.ioc().get(Dao.class,"dao");

        SysUser sysUser = dao.fetch(SysUser.class,Cnd.where(SysUser.ACCOUNT,"=",captchaInToken.getUsername()));
        if (null == sysUser){
            throw new IpErrorException("非法用户");
        }

        WhiteList whiteList = dao.fetch(WhiteList.class, Cnd.where(WhiteList.SYS_USER_ID, "=", sysUser.getUser_id()));
        //白名单无此用户
        if (null == whiteList){
            throw new IpErrorException("非法的用户ip");
        }
        String[] ips = whiteList.getWhite_ips().split(",");
        for (String ipEach : ips){
            if (ipEach.equals(requestIP)){
                flag = true;
                break;
            }
        }
        if (!flag){
            throw new IpErrorException("非法的登录ip");
        }

        //验证码验证
        if (null == captcha || "".equals(captcha.trim())){
            throw new CaptchaEmptyException("验证码不能为空");
        }

        //session中取出正确的验证码
        String sessionCaptcha = (String) SecurityUtils.getSubject().getSession().getAttribute("captcha");
        if (!sessionCaptcha.equals(captcha)){
            throw new CaptchaErrorException("验证码错误");
        }

        return super.doGetAuthenticationInfo(token);
    }

}
