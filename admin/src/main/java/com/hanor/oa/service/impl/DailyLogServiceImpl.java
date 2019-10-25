package com.hanor.oa.service.impl;

import com.hanor.entity.DailyLog;
import com.hanor.oa.base.Constants;
import com.hanor.oa.service.DailyLogService;
import com.wangzc.mvc.config.SysConfig;
import com.wangzc.mvc.data.Result;
import com.wangzc.mvc.entity.SysUserRole;
import com.wangzc.mvc.utils.SysUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@IocBean
public class DailyLogServiceImpl implements DailyLogService {

    @Inject
    private Dao dao;

    @Override
    public Object list(Integer user_id,Integer dept_Id) {
        List<SysUserRole> userRoles= dao.query(SysUserRole.class, Cnd.where("user_id","=",user_id));
        if (null == userRoles){
            return Result.fail("无日志访问权限");
        }
        Set<String> roleSet = new HashSet<>();
        for (SysUserRole sysUserRole :userRoles){
            roleSet.add(sysUserRole.getRole_id());
        }
        //管理员权限
        if (roleSet.contains(SysConfig.SYS_ROLE)){
            return Result.pagerList(DailyLog.class, SysUtils.autoCnd(DailyLog.class));
        }
        //部门经理权限
        if (roleSet.contains(Constants.DEPT_ROLE)){
            return Result.pagerList(DailyLog.class, SysUtils.autoCnd(DailyLog.class).and("dept_id","=",dept_Id));
        }
        //普通员工权限
        if (roleSet.contains(Constants.USER_ROLE)){
            return Result.pagerList(DailyLog.class, SysUtils.autoCnd(DailyLog.class).and("sys_user_id","=",user_id));
        }
        return Result.fail("无访问权限");
    }

    @Override
    public Object add(DailyLog dailyLog) {

        if (null == dailyLog.getDept_id() || null == dailyLog.getDept_name() || null == dailyLog.getUser_name()){
            return Result.fail("无添加日志权限");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        dailyLog.setLog_date(dateFormat.format(new java.util.Date()));
        dailyLog.setCreate_time(SysUtils.current());
        dao.insert(dailyLog);
        return Result.ok("日志添加成功");
    }
}
