package com.hanor.oa.controller;

import com.hanor.entity.Staff;
import com.hanor.oa.service.StaffService;
import com.wangzc.mvc.annotation.Desc;
import com.wangzc.mvc.config.SysConfig;
import com.wangzc.mvc.controller.BaseController;
import com.wangzc.mvc.data.Result;
import com.wangzc.mvc.entity.SysRole;
import com.wangzc.mvc.exception.AlertException;
import com.wangzc.mvc.utils.SysUtils;
import com.wangzc.mvc.view.maker.HtmlViewMaker;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Scope;
import org.nutz.mvc.annotation.*;

@IocBean
@At("/staff")
@Ok(HtmlViewMaker.VIEW_HTML)
public class StaffController extends BaseController {

	@Inject
	private StaffService staffService;

	@At
	@GET
	@RequiresPermissions("staff.list")
	@Desc("staff.列表")
	@Ok(HtmlViewMaker.VIEW_JSON)
	public Object select(){
		return dao.query(SysRole.class,SysUtils.autoCnd(SysRole.class));
	}

	@At
	@GET
	@RequiresPermissions("staff.list")
	@Desc("staff.列表")
	public Object list(@Attr(scope= Scope.SESSION, value= SysConfig.SYS_USER_ID)int staff_id) {
		return Result.pagerList(Staff.class, SysUtils.autoCnd(Staff.class));
	}

	@At
	@POST
	@RequiresPermissions("staff.add")
	@Desc("staff.添加")
	@Ok(HtmlViewMaker.VIEW_JSON)
	public Object add(@Param("..") Staff staff) throws AlertException {
		return staffService.addStaff(staff);
	}



}
