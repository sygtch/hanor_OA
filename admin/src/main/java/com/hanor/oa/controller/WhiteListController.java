package com.hanor.oa.controller;

import com.hanor.entity.WhiteList;
import com.hanor.oa.util.CheckTools;
import com.hanor.oa.util.StringUtils;
import com.wangzc.mvc.annotation.Desc;
import com.wangzc.mvc.controller.BaseController;
import com.wangzc.mvc.data.Result;
import com.wangzc.mvc.entity.SysUser;
import com.wangzc.mvc.exception.AlertException;
import com.wangzc.mvc.utils.SysUtils;
import com.wangzc.mvc.view.maker.HtmlViewMaker;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.*;

import java.util.HashSet;
import java.util.Set;

@IocBean
@At("/white/list")
@Ok(HtmlViewMaker.VIEW_HTML)
public class WhiteListController extends BaseController {

	@At
	@GET
	@RequiresPermissions("white.list.add")
	@Desc("登录账户白名单.下拉列表")
	@Ok(HtmlViewMaker.VIEW_JSON)
	public Object select(){
		return dao.query(SysUser.class, SysUtils.autoCnd(SysUser.class));
	}

	@At
	@GET
	@RequiresPermissions("white.list.list")
	@Desc("登录账户白名单.列表")
	public Object list() {
		return Result.pagerList(WhiteList.class, SysUtils.autoCnd(WhiteList.class));
	}

	@At
	@POST
	@RequiresPermissions("white.list.add")
	@Desc("登录账户白名单.添加")
	@Ok(HtmlViewMaker.VIEW_JSON)
	public Object add(@Param("..") WhiteList whiteList) throws AlertException {

		if (null == whiteList){
			throw new AlertException("无白名单信息，暂时无法添加");
		}
		if (null == whiteList.getSys_user_id() || 0 >= whiteList.getSys_user_id()){
			throw new AlertException("无用户id");
		}
		if (StringUtils.isEmpty(whiteList.getWhite_ips())){
			throw new AlertException("无用户ip");
		}
		//输入ip校验
		String[] ipsInput = whiteList.getWhite_ips().split(",");
		//ip去重
		Set<String> ipInputSet = new HashSet<>();
		for (String ipEach :ipsInput){
			if (!CheckTools.ipCheck(ipEach)){
				throw new AlertException("存在非法IP，请核对");
			}
			//前台传输ip去重
			ipInputSet.add(ipEach);
		}

		//取出数据库的白名单
		WhiteList userWhiteList = dao.fetch(WhiteList.class, Cnd.where(WhiteList.SYS_USER_ID,"=",whiteList.getSys_user_id()));
		//如果该用户曾经加过ip白名单
		if (null != userWhiteList){
			String ips = userWhiteList.getWhite_ips().trim();
			String[] ipsArray = ips.split(",");
			for (String ipArrayEach : ipsArray) {
				//前台传输和数据库综合去重
				ipInputSet.add(ipArrayEach);
			}
		}
		StringBuilder ipBuilder = new StringBuilder();
		for (String singleIp : ipInputSet){
			ipBuilder.append(singleIp + ",");
		}
		String ipString = ipBuilder.toString();
		String ipSave = ipString.substring(0,ipString.length() - 1);
		SysUser sysUser = dao.fetch(SysUser.class,whiteList.getSys_user_id());
		if (null == sysUser){
			throw new AlertException("非法用户");
		}
		whiteList.setWhite_ips(ipSave);
		whiteList.setUser_name(sysUser.getUser_name());
		whiteList.setCreate_time(SysUtils.current());
		dao.insertOrUpdate(whiteList);
		return Result.ok("添加成功");
	}

	@At
	@GET
	@RequiresPermissions("white.list.edit")
	@Desc("登录账户白名单.编辑")
	public Object edit(@Param("sys_user_id") Integer sys_user_id) {
		return dao.fetch(WhiteList.class, sys_user_id);
	}

	@At
	@POST
	@RequiresPermissions("white.list.edit")
	@Desc("登录账户白名单.编辑")
	@Ok(HtmlViewMaker.VIEW_JSON)
	public Object save(@Param("..") WhiteList whiteList) throws AlertException {

		if (null == whiteList){
			throw new AlertException("无白名单信息，暂时无法添加");
		}
		if (null == whiteList.getSys_user_id() || 0 >= whiteList.getSys_user_id()){
			throw new AlertException("无用户id");
		}
		if (StringUtils.isEmpty(whiteList.getWhite_ips())){
			throw new AlertException("无用户ip");
		}
		//输入ip校验
		String[] ipsInput = whiteList.getWhite_ips().split(",");
		for (String ipEach :ipsInput){
			if (!CheckTools.ipCheck(ipEach)){
				throw new AlertException("存在非法IP，请核对");
			}
		}
		dao.updateIgnoreNull(whiteList);
		return Result.ok("修改成功");
	}
}
