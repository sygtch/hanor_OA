package com.hanor.oa.service;

import com.hanor.entity.Dept;

public interface DeptService {

    /**
     * 部门列表
     * @param staff_id
     * @return
     */
    Object list(Integer staff_id);

    /**
     * 添加部门
     * @param dept
     * @return
     */
    Object add(Dept dept);

    /**
     * 编辑部门
     * @param dept
     * @return
     */
    Object edit(Dept dept);

    /**
     * 删除部门
     * @param dept_id
     * @return
     */
    Object delete(Integer dept_id);
}
