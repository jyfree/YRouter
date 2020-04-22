package com.jy.yrouter.core;


import androidx.annotation.NonNull;

/**
 * @description Postcard 分发完成的监听器
 * @date: 2020/4/2 11:18
 * @author: jy
 */
public interface OnCompleteListener extends ResultCode {

    /**
     * 分发成功
     */
    void onSuccess(@NonNull Postcard postcard);

    /**
     * 分发失败
     *
     * @param resultCode 错误代码，可参考 {@link ResultCode}
     */
    void onError(@NonNull Postcard postcard, int resultCode);
}
