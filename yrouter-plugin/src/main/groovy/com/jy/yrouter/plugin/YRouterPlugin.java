package com.jy.yrouter.plugin;


import com.android.build.gradle.BaseExtension;
import com.jy.yrouter.annotation.common.Const;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @description 插件所做工作：将注解生成器生成的初始化类汇总到ServiceInit_xxx，运行时直接调用ServiceLoaderInit
 * @date: 2020/4/26 11:58
 * @author: jy
 */
public class YRouterPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        //创建额外配置，用于gradle配置
        YRouterExtension extension = project.getExtensions().create(Const.NAME, YRouterExtension.class);

        YRouterLogger.info("register YRouter transform");
        project.getExtensions().findByType(BaseExtension.class).registerTransform(new YRouterTransform());

        project.afterEvaluate(p -> YRouterLogger.setConfig(extension));

    }
}
