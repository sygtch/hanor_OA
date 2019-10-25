package com.hanor.oa.service.impl;

import com.hanor.entity.Dept;
import com.hanor.entity.Staff;
import com.hanor.oa.service.DeptService;
import com.hanor.oa.util.StringUtils;
import com.wangzc.mvc.config.SysConfig;
import com.wangzc.mvc.data.Result;
import com.wangzc.mvc.entity.SysUserRole;
import com.wangzc.mvc.utils.SysUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.List;

@IocBean
public class DeptServiceImpl implements DeptService {
    @Inject
    private Dao dao;

    @Override
    public Object list(Integer user_id) {
        if (null == user_id){
            return Result.fail("无权限访问");
        }
        List<SysUserRole> sysUserRoles = dao.query(SysUserRole.class,Cnd.where(SysUserRole.USER_ID,"=",user_id));

        if (null != sysUserRoles){
            for (SysUserRole role : sysUserRoles){
                if (SysConfig.SYS_ROLE.equals(role.getRole_id())){
                    return Result.pagerList(Dept.class, SysUtils.autoCnd(Dept.class));
                }
            }
        }
        return Result.fail("无权限访问");
    }

    @Override
    public Object add(Dept dept) {
        if (null == dept){
            return Result.fail("部门信息不全，无法添加");
        }
        if (StringUtils.isEmpty(dept.getDept_name())){
            return Result.fail("没有部门名称，无法添加");
        }
        dept.setCreate_time(SysUtils.current());
        dao.insert(dept);
        return Result.ok("部门添加成功");
    }

    @Override
    public Object edit(Dept dept) {
        if (null == dept){
            return Result.fail("部门信息不全，无法修改");
        }
        if (null == dept.getDept_id() || 0 ==dept.getDept_id()){
            return Result.fail("没有部门id，无法修改");
        }
        if (StringUtils.isEmpty(dept.getDept_name())){
            return Result.fail("没有部门名称，无法修改");
        }
        dao.updateIgnoreNull(dept);
        return Result.ok("部门修改成功");
    }

    @Override
    public Object delete(Integer dept_id) {
        if (null == dept_id || 0 == dept_id){
            return Result.fail("无部门id，无法删除");
        }
        int staffInDeptNum = dao.count(Staff.class, Cnd.where(Staff.DEPT_ID,"=",dept_id));
        if (staffInDeptNum > 0){
            return Result.fail("部门下有员工，无法删除");
        }
        dao.delete(Dept.class, dept_id);
        return Result.ok("部门删除成功");
    }
}
