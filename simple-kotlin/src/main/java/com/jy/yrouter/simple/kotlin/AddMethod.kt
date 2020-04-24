package com.jy.yrouter.simple.kotlin

import com.jy.yrouter.annotation.RouterService
import com.jy.yrouter.appbaselibrary.DemoConstant
import com.jy.yrouter.method.Func2

/**
 * @description 接口实现类
 * @date: 2020/4/24 15:15
 * @author: jy
 */
@RouterService(interfaces = [Func2::class], key = [DemoConstant.ADD_METHOD], singleton = true)
class AddMethod : Func2<Int?, Int?, Int?> {

    override fun call(a: Int?, b: Int?): Int? {
        return if (a != null && b != null) a + b else -1
    }
}