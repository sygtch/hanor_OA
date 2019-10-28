package com.hanor.oa.controller;

import com.hanor.oa.base.Constants;
import com.hanor.oa.service.DailyLogService;
import com.wangzc.mvc.config.SysConfig;
import com.wangzc.mvc.utils.SysUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Scope;
import org.nutz.mvc.annotation.*;
import com.wangzc.mvc.annotation.Desc;
import com.wangzc.mvc.controller.BaseController;
import com.wangzc.mvc.data.Result;
import com.hanor.entity.DailyLog;
import com.wangzc.mvc.exception.AlertException;
import com.wangzc.mvc.view.maker.HtmlViewMaker;

@IocBean
@At("/daily/log")
@Ok(HtmlViewMaker.VIEW_HTML)
public class DailyLogController extends BaseController {

	@Inject
	private DailyLogService dailyLogService;
	@At
	@GET
	@RequiresPermissions("daily.log.list")
	@Desc("daily_log.列表")
	public Object list(@Attr(scope= Scope.SESSION, value= SysConfig.SYS_USER_ID)int staff_id,
					   @Attr(scope= Scope.SESSION, value= Constants.DEPT_ID)int dept_id) {
		return dailyLogService.list(staff_id,dept_id);
	}

	@At
	@POST
	@RequiresPermissions("daily.log.add")
	@Desc("daily_log.添加")
	@Ok(HtmlViewMaker.VIEW_JSON)
	public Object add(@Param("..") DailyLog dailylog,
					  @Attr(scope= Scope.SESSION, value= SysConfig.SYS_USER_ID)int sys_user_id,
					  @Attr(scope= Scope.SESSION, value= Constants.DEPT_ID)int dept_id,
					  @Attr(scope= Scope.SESSION, value= Constants.USER_NAME)String user_name,
					  @Attr(scope= Scope.SESSION, value= Constants.DEPT_NAME)String dept_name) throws AlertException {

		dailylog.setSys_user_id(sys_user_id);
		dailylog.setDept_id(dept_id);
		dailylog.setDept_name(dept_name);
		dailylog.setUser_name(user_name);

		return dailyLogService.add(dailylog);
	}

	@At
	@GET
	@RequiresPermissions("daily.log.info")
	@Desc("daily_log.详情")
	public Object edit(@Param("log_id") Integer log_id) {
		return dao.fetch(DailyLog.class, log_id);
	}



}
