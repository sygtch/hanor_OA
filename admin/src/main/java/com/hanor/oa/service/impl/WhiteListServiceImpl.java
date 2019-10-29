package com.hanor.oa.service.impl;

import com.hanor.entity.WhiteList;
import com.hanor.oa.service.WhiteListService;
import com.hanor.oa.util.CheckTools;
import com.hanor.oa.util.StringUtils;
import com.wangzc.mvc.data.Result;
import com.wangzc.mvc.entity.SysUser;
import com.wangzc.mvc.utils.SysUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

@IocBean
public class WhiteListServiceImpl implements WhiteListService {
    @Inject
    private Dao dao;

    @Override
    public Object add(WhiteList whiteList) {
        if (null == whiteList){
            return Result.fail("无白名单信息，暂时无法添加");
        }
        if (null == whiteList.getSys_user_id() || 0 >= whiteList.getSys_user_id()){
            return Result.fail("无用户id");
        }
        if (StringUtils.isEmpty(whiteList.getWhite_ips())){
            return Result.fail("无用户ip");
        }
        //输入ip校验
        String[] ipsInput = whiteList.getWhite_ips().split(",");
        for (String ipEach :ipsInput){
            if (!CheckTools.ipCheck(ipEach)){
                return Result.fail("存在非法IP，请核对");
            }
        }

        //取出数据库的白名单
        WhiteList userWhiteList = dao.fetch(WhiteList.class, Cnd.where(WhiteList.SYS_USER_ID,"=",whiteList.getSys_user_id()));
        //如果该用户曾经加过ip白名单
        if (null != userWhiteList){
            String ips = userWhiteList.getWhite_ips().trim();
            ips += "," + whiteList.getWhite_ips();
            whiteList.setWhite_ips(ips);
        }
        SysUser sysUser = dao.fetch(SysUser.class,whiteList.getSys_user_id());
        whiteList.setUser_name(sysUser.getUser_name());
        whiteList.setCreate_time(SysUtils.current());
        dao.delete(whiteList);
        dao.insert(whiteList);

        return Result.ok("添加成功");
    }

    @Override
    public Object edit(WhiteList whiteList) {
        if (null == whiteList){
            return Result.fail("无白名单信息，暂时无法添加");
        }
        if (null == whiteList.getSys_user_id() || 0 >= whiteList.getSys_user_id()){
            return Result.fail("无用户id");
        }
        if (StringUtils.isEmpty(whiteList.getWhite_ips())){
            return Result.fail("无用户ip");
        }
        //输入ip校验
        String[] ipsInput = whiteList.getWhite_ips().split(",");
        for (String ipEach :ipsInput){
            if (!CheckTools.ipCheck(ipEach)){
                return Result.fail("存在非法IP，请核对");
            }
        }
        dao.updateIgnoreNull(whiteList);
        return Result.ok("修改成功");
    }
}
