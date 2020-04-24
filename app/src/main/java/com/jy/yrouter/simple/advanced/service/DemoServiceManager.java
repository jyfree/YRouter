package com.jy.yrouter.simple.advanced.service;

import com.jy.yrouter.Router;
import com.jy.yrouter.simple.constant.DemoConstant;

/**
 * @description 服务管理，可获取接口实现类
 * @date: 2020/4/24 14:53
 * @author: jy
 */
public class DemoServiceManager {
    public static IAccountService getAccountService() {
        return Router.getService(IAccountService.class, DemoConstant.SINGLETON);
    }
}
