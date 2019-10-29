package com.hanor.oa.service;

import com.hanor.entity.WhiteList;
import com.wangzc.mvc.exception.AlertException;

public interface WhiteListService {

    /**
     * 添加白名单
     * @param whiteList
     * @return
     */
    Object add(WhiteList whiteList) throws AlertException;

    /**
     * 修改白名单
     * @param whiteList
     * @return
     */
    Object edit(WhiteList whiteList) throws AlertException;
}
