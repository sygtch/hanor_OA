package com.hanor.oa.service;

import com.hanor.entity.Staff;
import com.wangzc.mvc.exception.AlertException;

public interface StaffService {



    /**
     * 增加员工信息
     * @param staff
     * @return
     */
    Object addStaff(Staff staff) throws AlertException;
}
