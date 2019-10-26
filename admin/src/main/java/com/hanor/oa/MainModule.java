package com.hanor.oa;

import com.wangzc.mvc.config.SysConfig;
import com.wangzc.mvc.view.maker.HtmlViewMaker;
import org.nutz.integration.shiro.ShiroSessionProvider;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.ioc.provider.ComboIocProvider;
@SetupBy(value = MainSetup.class)
@SessionBy(ShiroSessionProvider.class)
@IocBy(type = ComboIocProvider.class, args = {"*js", "ioc/", "*anno", MainModule.GROUP_SCAN,SysConfig.PACKAGE_NAME,
        "*tx"})
@Modules(scanPackage = true, packages = {SysConfig.PACKAGE_NAME})
@ChainBy(args = "mvc/chain.js")
@Encoding(input = org.nutz.lang.Encoding.UTF8, output = org.nutz.lang.Encoding.UTF8)
@Views(HtmlViewMaker.class)
@Fail(HtmlViewMaker.VIEW_FAIL)
@Ok(HtmlViewMaker.VIEW_HTML)
@Localization(value = "msg/", defaultLocalizationKey = "zh-CN")
public class MainModule {

    public static final String GROUP_SCAN = "com.hanor.oa";
}
