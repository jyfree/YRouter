package com.jy.yrouter.core;

import androidx.annotation.NonNull;

import com.jy.yrouter.utils.PriorityList;

import java.util.Iterator;

/**
 * @description 路由处理器责任链基类
 * @date: 2020/4/2 13:33
 * @author: jy
 */
public class BaseChainedHandler extends PostcardHandler {

    /**
     * 路由处理器责任链表
     */
    private final PriorityList<PostcardHandler> mHandlers = new PriorityList<>();

    /**
     * 添加一个Handler
     *
     * @param priority 优先级。优先级越大越先执行；相同优先级，先加入的先执行。
     */
    public BaseChainedHandler addChildHandler(@NonNull PostcardHandler handler, int priority) {
        mHandlers.addItem(handler, priority);
        return this;
    }

    /**
     * 添加一个Handler，优先级为0
     */
    public BaseChainedHandler addChildHandler(@NonNull PostcardHandler handler) {
        return addChildHandler(handler, 0);
    }

    @Override
    protected boolean shouldHandle(@NonNull Postcard postcard) {
        return !mHandlers.isEmpty();
    }

    @Override
    protected void handleInternal(@NonNull Postcard postcard, @NonNull InterceptCallback callback) {
        next(mHandlers.iterator(), postcard, callback);
    }

    private void next(@NonNull final Iterator<PostcardHandler> iterator, @NonNull final Postcard postcard,
                      @NonNull final InterceptCallback callback) {
        if (iterator.hasNext()) {
            PostcardHandler t = iterator.next();
            t.handle(postcard, new InterceptCallback() {
                @Override
                public void onNext() {
                    next(iterator, postcard, callback);
                }

                @Override
                public void onComplete(int resultCode) {
                    callback.onComplete(resultCode);
                }
            });
        } else {
            callback.onNext();
        }
    }
}
