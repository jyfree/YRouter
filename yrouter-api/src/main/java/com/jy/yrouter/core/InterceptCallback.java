package com.jy.yrouter.core;

/**
 * @description 拦截器回调
 * @date: 2020/4/2 11:03
 * @author: jy
 */
public interface InterceptCallback extends ResultCode {

    /**
     * 处理完成，继续后续流程。
     */
    void onNext();

    /**
     * 处理完成，终止分发流程。
     *
     * @param resultCode 结果，可参考 {@link ResultCode}
     */
    void onComplete(int resultCode);
}
