package com.hanor.oa.base;


import com.wangzc.mvc.config.SysConfig;

public interface Constants extends SysConfig {

    /**
     * 超级管理员角色id
     */
    String USER_ROLE = "staff";
    /**
     * 部门经理角色id
     */
    String DEPT_ROLE = "dept";

    /**
     * session存储的系统用户name键值
     */
    String USER_NAME = "_user_name";

    /**
     * session存储的系统用户的部门id键值
     */
    String DEPT_ID = "_dept_id";

    /**
     * session存储的系统用户的部门名称键值
     */
    String DEPT_NAME = "_dept_name";
}
