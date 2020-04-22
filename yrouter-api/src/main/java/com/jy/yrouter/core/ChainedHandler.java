package com.jy.yrouter.core;

import android.content.Context;

import androidx.annotation.NonNull;

import com.jy.yrouter.utils.RLogUtils;

/**
 * @description 路由处理器责任链
 * @date: 2020/4/2 14:09
 * @author: jy
 */
public class ChainedHandler extends BaseChainedHandler {

    private final Context mContext;

    public ChainedHandler(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 全局回调
     */
    private OnCompleteListener mGlobalOnCompleteListener;

    /**
     * 全局 {@link OnCompleteListener}
     */
    public void setGlobalOnCompleteListener(OnCompleteListener listener) {
        mGlobalOnCompleteListener = listener;
    }

    public OnCompleteListener getGlobalOnCompleteListener() {
        return mGlobalOnCompleteListener;
    }

    public void startPostcard(Postcard postcard) {
        if (postcard == null) {
            String msg = "Postcard为空";
            Postcard req = new Postcard(mContext, "").setErrorMessage(msg);
            onError(req, ResultCode.CODE_BAD_REQUEST);
        } else if (postcard.isUriEmpty()) {

            String msg = "跳转链接为空";
            postcard.setErrorMessage(msg);
            onError(postcard, ResultCode.CODE_BAD_REQUEST);

        } else {
            RLogUtils.i("接收到路由请求: %s", postcard.toFullString());
            handle(postcard, new RootInterceptCallback(postcard));
        }
    }

    private void onSuccess(@NonNull Postcard postcard) {
        //全局回调
        OnCompleteListener globalListener = mGlobalOnCompleteListener;
        if (globalListener != null) {
            globalListener.onSuccess(postcard);
        }
        //单个路由容器回调Postcard
        final OnCompleteListener listener = postcard.getOnCompleteListener();
        if (listener != null) {
            listener.onSuccess(postcard);
        }
    }

    private void onError(@NonNull Postcard postcard, int resultCode) {
        OnCompleteListener globalListener = mGlobalOnCompleteListener;
        if (globalListener != null) {
            globalListener.onError(postcard, resultCode);
        }
        final OnCompleteListener listener = postcard.getOnCompleteListener();
        if (listener != null) {
            listener.onError(postcard, resultCode);
        }
    }


    protected class RootInterceptCallback implements InterceptCallback {

        private final Postcard postcard;

        RootInterceptCallback(Postcard postcard) {
            this.postcard = postcard;
        }

        @Override
        public void onNext() {
            onComplete(CODE_NOT_FOUND);
        }

        @Override
        public void onComplete(int resultCode) {
            switch (resultCode) {

                case CODE_REDIRECT:
                    // 重定向，重新跳转
                    RLogUtils.i("重定向, result code = %s", resultCode);
                    startPostcard(postcard);
                    break;

                case CODE_SUCCESS:
                    // 跳转成功
                    postcard.putField(Postcard.FIELD_RESULT_CODE, resultCode);
                    onSuccess(postcard);
                    RLogUtils.i("跳转成功, result code = %s", resultCode);
                    break;

                default:
                    // 跳转失败
                    postcard.putField(Postcard.FIELD_RESULT_CODE, resultCode);
                    onError(postcard, resultCode);
                    RLogUtils.i("跳转失败, result code = %s", resultCode);
                    break;
            }
        }
    }
}
