package com.hanor.oa.service.impl;

import com.hanor.entity.Dept;
import com.hanor.entity.Staff;
import com.hanor.oa.service.StaffService;
import com.hanor.oa.util.StringUtils;
import com.wangzc.mvc.data.Result;
import com.wangzc.mvc.entity.SysUser;
import com.wangzc.mvc.entity.SysUserRole;
import com.wangzc.mvc.exception.AlertException;
import com.wangzc.mvc.service.SysUserService;
import com.wangzc.mvc.utils.SysUtils;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.List;

@IocBean
public class StaffServiceImpl implements StaffService {

    @Inject
    private SysUserService sysUserService;
    @Inject
    private Dao dao;

    @Override
    public Object addStaff(Staff staff) throws AlertException {

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
