package com.hanor.oa.service;

import com.hanor.entity.WhiteList;

public interface WhiteListService {

    /**
     * 添加白名单
     * @param whiteList
     * @return
     */
    Object add(WhiteList whiteList);

    /**
     * 修改白名单
     * @param whiteList
     * @return
     */
    Object edit(WhiteList whiteList);
}
