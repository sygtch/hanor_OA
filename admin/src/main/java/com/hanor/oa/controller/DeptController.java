package com.hanor.oa.controller;

import com.hanor.entity.Dept;
import com.hanor.oa.service.DeptService;
import com.wangzc.mvc.annotation.Desc;
import com.wangzc.mvc.config.SysConfig;
import com.wangzc.mvc.controller.BaseController;
import com.wangzc.mvc.exception.AlertException;
import com.wangzc.mvc.utils.SysUtils;
import com.wangzc.mvc.view.maker.HtmlViewMaker;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Scope;
import org.nutz.mvc.annotation.*;

@IocBean
@At("/dept")
@Ok(HtmlViewMaker.VIEW_HTML)
public class DeptController extends BaseController {

	@Inject
	private DeptService deptService;

	@At
	@GET
	@RequiresPermissions("dept.list")
	@Desc("部门.下拉列表")
	@Ok(HtmlViewMaker.VIEW_JSON)
	public Object select(){
		return dao.query(Dept.class, SysUtils.autoCnd(Dept.class).orderBy("sort_no","asc"));
	}

	@At
	@GET
	@RequiresPermissions("dept.list")
	@Desc("部门.列表")
	public Object list(@Attr(scope= Scope.SESSION, value= SysConfig.SYS_USER_ID)int user_id) throws AlertException {
		return deptService.list(user_id);
	}

	@At
	@POST
	@RequiresPermissions("dept.add")
	@Desc("部门.添加")
	@Ok(HtmlViewMaker.VIEW_JSON)
	public Object add(@Param("..") Dept dept) throws AlertException {
		return deptService.add(dept);
	}

	@At
	@GET
	@RequiresPermissions("dept.edit")
	@Desc("部门.编辑")
	public Object edit(@Param("dept_id") Integer dept_id) {
		return dao.fetch(Dept.class, dept_id);
	}

	@At
	@POST
	@RequiresPermissions("dept.edit")
	@Desc("部门.编辑")
	@Ok(HtmlViewMaker.VIEW_JSON)
	public Object save(@Param("..") Dept dept) throws AlertException {

		return deptService.edit(dept);
	}

	@At
	@GET
	@RequiresPermissions("dept.delete")
	@Desc("部门.删除")
	@Ok(HtmlViewMaker.VIEW_JSON)
	public Object delete(@Param("dept_id") Integer dept_id) throws AlertException {
		return deptService.delete(dept_id);
	}

}
