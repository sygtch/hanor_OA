package com.hanor.oa.shiro.filter;

import com.hanor.entity.Staff;
import com.hanor.oa.base.Constants;
import com.hanor.oa.shiro.token.CaptchaInToken;
import com.wangzc.mvc.shiro.filter.AuthenticationFilter;
import com.wangzc.mvc.utils.SysUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
public class StaffLoginFilter extends AuthenticationFilter {

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        //取出dao
        Dao dao = SysUtils.ioc().get(Dao.class,"dao");
        //通过shiro方法取出用户id
        Integer user_id = (Integer) subject.getPrincipal();
        if (null == user_id || user_id <= 0){
            return false;
        }
        //取出当前用户的员工信息
        Staff staff = dao.fetch(Staff.class, Cnd.where(Staff.SYS_USER_ID,"=",user_id));
        if (staff != null){
            ((HttpServletRequest) request).getSession().setAttribute(Constants.USER_NAME, staff.getStaff_name());
            ((HttpServletRequest) request).getSession().setAttribute(Constants.DEPT_ID, staff.getDept_id());
            ((HttpServletRequest) request).getSession().setAttribute(Constants.DEPT_NAME, staff.getDept_name());
        }

        return super.onLoginSuccess(token, subject, request, response);
    }

    @Override
    protected AuthenticationToken createToken(String username, String password, ServletRequest request, ServletResponse response) {

        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        String captchaCode = request.getParameter("captcha");
        //使用自定义token
        return new CaptchaInToken(username, password, host, rememberMe, captchaCode);
    }
}
