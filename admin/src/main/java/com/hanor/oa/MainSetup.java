package com.hanor.oa;

import com.wangzc.mvc.entity.SysRole;
import com.wangzc.mvc.exception.AlertException;
import com.wangzc.mvc.service.SysRolePermissionService;
import com.wangzc.mvc.setup.AbstractSetup;
import com.wangzc.mvc.setup.init.Init;
import com.wangzc.mvc.setup.init.InitRoleAndPermission;
import com.wangzc.mvc.setup.init.InitTable;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
public class MainSetup extends AbstractSetup {
    @Override
    protected void init(NutConfig nc, Ioc ioc) throws AlertException {
        // 初始化角色和权限
        Init.add(new InitRoleAndPermission("com.hanor"));
        // 初始化数据库表和字段
        Init.add(new InitTable("com.hanor"));

        Dao dao = ioc.get(Dao.class);
        Daos.migration(dao, SysRole.class, true, false, false);
        SysRolePermissionService sysRolePermissionService =ioc.get(SysRolePermissionService.class);
        int deptIsExisted = dao.count(SysRole.class, Cnd.where(SysRole.ROLE_ID,"=","dept"));
        int userIsExisted = dao.count(SysRole.class, Cnd.where(SysRole.ROLE_ID,"=","dept"));
        // 初始化新增角色
        if (deptIsExisted == 0) {
            sysRolePermissionService.addRole("dept","部门经理");
        }
        if (userIsExisted == 0){
            sysRolePermissionService.addRole("staff","普通员工");
        }
    }
    @Override
    public void destroy(NutConfig nc) {
    }
}
