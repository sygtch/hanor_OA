package com.hanor.oa;

import com.wangzc.template.TemplateConfig;
import com.wangzc.template.TemplateProcessor;
import com.wangzc.template.creator.ace.*;
import com.wangzc.template.exception.TableUnfindException;

public class Template {

    public static void main(String[] args) throws TableUnfindException {
        // 配置
        TemplateConfig config = new TemplateConfig();
        config.setDb_name("hanor_oa");// 数据库名称
        config.setDb_host("localhost:3306");//
        config.setDb_username("root");//
        config.setDb_password("123456");//
        config.setProject_path("D:\\HanorWorkspace\\oa\\admin");// 主模块项目目录(非必填，可在执行时选择)
        config.setProject_package("com.hanor.oa");// 主模块项目包名
        config.setProject_module_path("D:\\HanorWorkspace\\oa\\pojo");// 子模块项目目录(非必填，可在执行时选择)
        config.setProject_module_package("com.hanor");// 子模块项目包名
        config.setAuthor("郭天驰");// 作者
        TemplateProcessor processor = new TemplateProcessor(config);
        // 加载模板生成器
        // entity.java生成
        processor.addCreator(new CreatorEntity());
        // controller.java生成
        processor.addCreator(new CreatorController());
        // list.html生成
        processor.addCreator(new CreatorHtmlList());
        // add.html生成
        processor.addCreator(new CreatorHtmlAdd());
        // edit.html生成
        processor.addCreator(new CreatorHtmlEdit());
        // 加载表并创建文件  todo 表名
        //processor.load("dept").create();
        //processor.load("staff").create();
        //processor.load("daily_log").create();
        processor.load("white_list").create();
    }
}
