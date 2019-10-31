package com.hanor.oa.controller;

import com.hanor.entity.DailyLog;
import com.hanor.oa.base.Constants;
import com.wangzc.mvc.annotation.Desc;
import com.wangzc.mvc.config.SysConfig;
import com.wangzc.mvc.controller.BaseController;
import com.wangzc.mvc.data.Result;
import com.wangzc.mvc.entity.SysUserRole;
import com.wangzc.mvc.exception.AlertException;
import com.wangzc.mvc.utils.SysUtils;
import com.wangzc.mvc.view.maker.HtmlViewMaker;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Scope;
import org.nutz.mvc.annotation.*;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@IocBean
@At("/daily/log")
@Ok(HtmlViewMaker.VIEW_HTML)
public class DailyLogController extends BaseController {

	@At
	@GET
	@RequiresPermissions("daily.log.list")
	@Desc("工作日志.列表")
	public Object list(@Attr(scope= Scope.SESSION, value= SysConfig.SYS_USER_ID)int staff_id,
					   @Attr(scope= Scope.SESSION, value= Constants.DEPT_ID)int dept_id) throws AlertException {

		List<SysUserRole> userRoles= dao.query(SysUserRole.class, Cnd.where("user_id","=",staff_id));
		if (null == userRoles){
			throw new AlertException("无日志访问权限");
		}
		Set<String> roleSet = new HashSet<>();
		for (SysUserRole sysUserRole :userRoles){
			roleSet.add(sysUserRole.getRole_id());
		}

		Cnd cnd = SysUtils.autoCnd(DailyLog.class);
		//管理员权限
		if (roleSet.contains(SysConfig.SYS_ROLE)){
			cnd.orderBy("create_time","desc");
			return Result.pagerList(DailyLog.class, cnd);
		}
		//部门经理权限
		if (roleSet.contains(Constants.DEPT_ROLE)){
			cnd.and("dept_id","=",dept_id).orderBy("create_time","desc");
			return Result.pagerList(DailyLog.class, cnd);
		}
		//普通员工权限
		if (roleSet.contains(Constants.USER_ROLE)){
			cnd.and("sys_user_id","=",staff_id).orderBy("create_time","desc");
			return Result.pagerList(DailyLog.class, cnd);
		}
		throw new AlertException("无访问权限");
	}

	@At
	@POST
	@RequiresPermissions("daily.log.add")
	@Desc("工作日志.添加")
	@Ok(HtmlViewMaker.VIEW_JSON)
	public Object add(@Param("..") DailyLog dailyLog,
					  @Attr(scope= Scope.SESSION, value= SysConfig.SYS_USER_ID)int sys_user_id,
					  @Attr(scope= Scope.SESSION, value= Constants.DEPT_ID)int dept_id,
					  @Attr(scope= Scope.SESSION, value= Constants.USER_NAME)String user_name,
					  @Attr(scope= Scope.SESSION, value= Constants.DEPT_NAME)String dept_name) throws AlertException {

		dailyLog.setSys_user_id(sys_user_id);
		dailyLog.setDept_id(dept_id);
		dailyLog.setDept_name(dept_name);
		dailyLog.setUser_name(user_name);

		if (null == dailyLog.getDept_id() || null == dailyLog.getDept_name() || null == dailyLog.getUser_name()){
			throw new AlertException("非员工账户登录，不可添加工作日志");
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		dailyLog.setLog_date(dateFormat.format(new java.util.Date()));
		dailyLog.setCreate_time(SysUtils.current());
		dao.insert(dailyLog);
		return Result.ok("日志添加成功");

	}

	@At
	@GET
	@RequiresPermissions("daily.log.info")
	@Desc("工作日志.详情")
	public Object edit(@Param("log_id") Integer log_id) {
		return dao.fetch(DailyLog.class, log_id);
	}



}
