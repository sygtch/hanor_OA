package com.hanor.oa.controller;

import com.hanor.oa.service.WhiteListService;
import com.wangzc.mvc.entity.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.wangzc.mvc.annotation.Desc;
import com.wangzc.mvc.controller.BaseController;
import com.wangzc.mvc.data.Result;
import com.hanor.entity.WhiteList;
import com.wangzc.mvc.exception.AlertException;
import com.wangzc.mvc.utils.SysUtils;
import com.wangzc.mvc.view.maker.HtmlViewMaker;

@IocBean
@At("/white/list")
@Ok(HtmlViewMaker.VIEW_HTML)
public class WhiteListController extends BaseController {

	@Inject
	private WhiteListService whiteListService;

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
	public Object add(@Param("..") WhiteList whitelist) throws AlertException {

		return whiteListService.add(whitelist);
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
	public Object save(@Param("..") WhiteList whitelist) throws AlertException {

		return whiteListService.edit(whitelist);
	}


}
