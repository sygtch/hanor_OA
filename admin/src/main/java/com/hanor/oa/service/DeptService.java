package com.hanor.oa.service;

import com.hanor.entity.Dept;
import com.wangzc.mvc.exception.AlertException;

public interface DeptService {

    /**
     * 部门列表
     * @param staff_id
     * @return
     */
    Object list(Integer staff_id) throws AlertException;

    /**
     * 添加部门
     * @param dept
     * @return
     */
    Object add(Dept dept) throws AlertException;

    /**
     * 编辑部门
     * @param dept
     * @return
     */
    Object edit(Dept dept) throws AlertException;

    /**
     * 删除部门
     * @param dept_id
     * @return
     */
    Object delete(Integer dept_id) throws AlertException;
}
