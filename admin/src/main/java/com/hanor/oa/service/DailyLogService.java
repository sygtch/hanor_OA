package com.hanor.oa.service;

import com.hanor.entity.DailyLog;

public interface DailyLogService {

    /**
     * 查询日志列表
     * @param staff_id
     * @return
     */
    Object list(Integer staff_id,Integer dept_id);

    /**
     * 添加工作日志
     * @param dailyLog
     * @return
     */
    Object add(DailyLog dailyLog);

}
