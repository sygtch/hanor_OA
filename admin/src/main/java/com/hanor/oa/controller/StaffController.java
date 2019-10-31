package com.hanor.oa.controller;

import com.hanor.entity.Dept;
import com.hanor.entity.Staff;
import com.hanor.oa.util.StringUtils;
import com.wangzc.mvc.annotation.Desc;
import com.wangzc.mvc.config.SysConfig;
import com.wangzc.mvc.controller.BaseController;
import com.wangzc.mvc.data.Result;
import com.wangzc.mvc.entity.SysRole;
import com.wangzc.mvc.entity.SysUser;
import com.wangzc.mvc.entity.SysUserRole;
import com.wangzc.mvc.exception.AlertException;
import com.wangzc.mvc.service.SysUserService;
import com.wangzc.mvc.utils.SysUtils;
import com.wangzc.mvc.view.maker.HtmlViewMaker;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Scope;
import org.nutz.mvc.annotation.*;

import java.util.List;

@IocBean
@At("/staff")
@Ok(HtmlViewMaker.VIEW_HTML)
public class StaffController extends BaseController {

	@Inject
	private SysUserService sysUserService;

	@At
	@GET
	@RequiresPermissions("staff.list")
	@Desc("员工.列表")
	@Ok(HtmlViewMaker.VIEW_JSON)
	public Object select(){
		return dao.query(SysRole.class,SysUtils.autoCnd(SysRole.class));
	}

	@At
	@GET
	@RequiresPermissions("staff.list")
	@Desc("员工.列表")
	public Object list(@Attr(scope= Scope.SESSION, value= SysConfig.SYS_USER_ID)int staff_id) {
		return Result.pagerList(Staff.class, SysUtils.autoCnd(Staff.class));
	}

	@At
	@POST
	@RequiresPermissions("staff.add")
	@Desc("员工.添加")
	@Ok(HtmlViewMaker.VIEW_JSON)
	public Object add(@Param("..") Staff staff) throws AlertException {
		List<Dept> depts = dao.query(Dept.class,null);
		if (depts.size() <= 0){
			throw new AlertException("请先添加至少一个部门");
		}
		if (null == staff){
			throw new AlertException("无添加信息，请确认");
		}
		if (StringUtils.isEmpty(staff.getStaff_name())){
			throw new AlertException("无员工姓名");
		}
		if (StringUtils.isEmpty(staff.getStaff_account())){
			throw new AlertException("无员工账户");
		}
		if (StringUtils.isEmpty(staff.getStaff_phone())){
			throw new AlertException("无员工电话");
		}
		if (StringUtils.isEmpty(staff.getStaff_type())){
			throw new AlertException("无员工类型");
		}
		if (null == staff.getDept_id() || 0 == staff.getDept_id()){
			throw new AlertException("无部门id");
		}
		//将员工账号/名字/密码等加入sys_user表
		SysUser sysUser = sysUserService.addUser(staff.getStaff_account(),staff.getStaff_name(),"123456");
		Dept dept = dao.fetch(Dept.class,staff.getDept_id());

		staff.setDept_name(dept.getDept_name());
		staff.setSys_user_id(sysUser.getUser_id());
		staff.setCreate_time(SysUtils.current());
		//将员工所有信息加入staff表
		dao.insert(staff);

		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setUser_id(sysUser.getUser_id());
		sysUserRole.setRole_id(staff.getStaff_type());
		//将员工角色加入sys_user_role表
		dao.insert(sysUserRole);
		return Result.ok("添加成功");
	}

}
