package com.hanor.oa;

import com.hanor.entity.WhiteList;
import com.hanor.oa.base.Constants;
import com.wangzc.mvc.config.SysConfig;
import com.wangzc.mvc.entity.SysRole;
import com.wangzc.mvc.entity.SysUser;
import com.wangzc.mvc.exception.AlertException;
import com.wangzc.mvc.service.SysRolePermissionService;
import com.wangzc.mvc.setup.AbstractSetup;
import com.wangzc.mvc.setup.init.Init;
import com.wangzc.mvc.setup.init.InitRoleAndPermission;
import com.wangzc.mvc.setup.init.InitTable;
import com.wangzc.mvc.utils.SysUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
public class MainSetup extends AbstractSetup {
    @Override
    protected void init(NutConfig nc, Ioc ioc) throws AlertException {

        // 初始化角色和权限
        Init.add(new InitRoleAndPermission(MainModule.GROUP_SCAN));
        // 初始化数据库表和字段
        Init.add(new InitTable(MainModule.GROUP_SCAN));

        //取dao
        Dao dao = ioc.get(Dao.class);
        //对表的更改级别设置
        Daos.migration(dao, SysRole.class, true, false, false);

        int deptIsExisted = dao.count(SysRole.class, Cnd.where(SysRole.ROLE_ID,"=","dept"));
        int userIsExisted = dao.count(SysRole.class, Cnd.where(SysRole.ROLE_ID,"=","dept"));
        int adminIpIsExisted = dao.count(WhiteList.class,Cnd.where(WhiteList.USER_NAME,"=", Constants.ADMIN_NAME));

        SysRolePermissionService sysRolePermissionService =ioc.get(SysRolePermissionService.class);
        // 初始化新增角色
        if (deptIsExisted == 0) {
            sysRolePermissionService.addRole("dept","部门经理");
        }
        if (userIsExisted == 0){
            sysRolePermissionService.addRole("staff","普通员工");
        }
        //初始化admin的白名单
        if (adminIpIsExisted == 0){
            SysUser adminUser = dao.fetch(SysUser.class,Cnd.where(SysUser.ACCOUNT,"=",SysConfig.SYS_ROLE));
            WhiteList whiteList = new WhiteList();
            whiteList.setSys_user_id(adminUser.getUser_id());
            whiteList.setUser_name(SysConfig.SYS_ROLE);
            whiteList.setWhite_ips(Constants.ADMIN_IP);
            whiteList.setCreate_time(SysUtils.current());
            dao.insert(whiteList);
        }
    }
    @Override
    public void destroy(NutConfig nc) {
    }
}
