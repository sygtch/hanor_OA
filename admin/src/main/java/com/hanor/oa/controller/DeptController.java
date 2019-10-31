package com.hanor.oa.controller;

import com.hanor.entity.Dept;
import com.hanor.entity.Staff;
import com.hanor.oa.util.StringUtils;
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
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import java.util.List;

@IocBean
@At("/dept")
@Ok(HtmlViewMaker.VIEW_HTML)
public class DeptController extends BaseController {

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
	public Object list(@Attr(scope= Scope.SESSION, value= SysConfig.SYS_USER_ID)Integer user_id) throws AlertException {
		if (null == user_id){
			throw new AlertException("无权限访问");
		}
		List<SysUserRole> sysUserRoles = dao.query(SysUserRole.class, Cnd.where(SysUserRole.USER_ID,"=",user_id));

		if (null != sysUserRoles){
			for (SysUserRole role : sysUserRoles){
				if (SysConfig.SYS_ROLE.equals(role.getRole_id())){
					return Result.pagerList(Dept.class, SysUtils.autoCnd(Dept.class));
				}
			}
		}
		throw new AlertException("无权限访问");
	}

	@At
	@POST
	@RequiresPermissions("dept.add")
	@Desc("部门.添加")
	@Ok(HtmlViewMaker.VIEW_JSON)
	public Object add(@Param("..") Dept dept) throws AlertException {
		if (null == dept){
			throw new AlertException("部门信息不全，无法添加");
		}
		if (StringUtils.isEmpty(dept.getDept_name())){
			throw new AlertException("没有部门名称，无法添加");
		}
		int deptIsExist = dao.count(Staff.class,Cnd.where(Dept.DEPT_NAME,"=",dept.getDept_name()));
		if (deptIsExist > 0){
			throw new AlertException("部门名称重复");
		}
		dept.setCreate_time(SysUtils.current());
		dao.insert(dept);
		return Result.ok("部门添加成功");
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
		if (null == dept){
			throw new AlertException("部门信息不全，无法修改");
		}
		if (null == dept.getDept_id() || 0 ==dept.getDept_id()){
			throw new AlertException("没有部门id，无法修改");
		}
		if (StringUtils.isEmpty(dept.getDept_name())){
			throw new AlertException("没有部门名称，无法修改");
		}
		//验证部门名称重复
		int deptSameName = dao.count(Dept.class,Cnd.where(Dept.DEPT_NAME,"=",dept.getDept_name()).and(Dept.DEPT_ID, "!=", dept.getDept_id()));
		if (deptSameName > 0){
			throw new AlertException("部门名称重复");
		}
		//员工的部门名称更改
		List<Staff> staffList = dao.query(Staff.class,Cnd.where(Dept.DEPT_ID,"=",dept.getDept_id()));
		for (Staff staff : staffList){
			staff.setDept_name(dept.getDept_name());
		}
		Trans.exec(
				new Atom() {
					@Override
					public void run() {
						dao.updateIgnoreNull(dept);
						dao.updateIgnoreNull(staffList);
					}
				}
		);
		return Result.ok("部门修改成功");
	}

	@At
	@GET
	@RequiresPermissions("dept.delete")
	@Desc("部门.删除")
	@Ok(HtmlViewMaker.VIEW_JSON)
	public Object delete(@Param("dept_id") Integer dept_id) throws AlertException {
		if (null == dept_id || 0 == dept_id){
			throw new AlertException("无部门id，无法删除");
		}
		int staffInDeptNum = dao.count(Staff.class, Cnd.where(Staff.DEPT_ID,"=",dept_id));
		if (staffInDeptNum > 0){
			throw new AlertException("部门下有员工，无法删除");
		}
		dao.delete(Dept.class, dept_id);
		return Result.ok("部门删除成功");
	}

}
