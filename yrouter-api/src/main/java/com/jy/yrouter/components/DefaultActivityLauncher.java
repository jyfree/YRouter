package com.jy.yrouter.components;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.jy.yrouter.activity.StartActivityAction;
import com.jy.yrouter.core.Postcard;
import com.jy.yrouter.core.ResultCode;
import com.jy.yrouter.utils.RLogUtils;

import java.util.List;

/**
 * @description 启动Activity的默认实现
 * @date: 2020/4/20 16:38
 * @author: jy
 */
public class DefaultActivityLauncher implements ActivityLauncher {
    public static final DefaultActivityLauncher INSTANCE = new DefaultActivityLauncher();

    private boolean mCheckIntentFirst = false;

    /**
     * 跳转前是否先检查Intent
     */
    public void setCheckIntentFirst(boolean checkIntentFirst) {
        mCheckIntentFirst = checkIntentFirst;
    }

    @Override
    public int startActivity(@NonNull Postcard postcard, @NonNull Intent intent) {

        Context context = postcard.getContext();

        // Extra
        Bundle extra = postcard.getField(Bundle.class, FIELD_INTENT_EXTRA);
        if (extra != null) {
            intent.putExtras(extra);
        }

        // Flags
        Integer flags = postcard.getField(Integer.class, FIELD_START_ACTIVITY_FLAGS);
        if (flags != null) {
            intent.setFlags(flags);
        }

        // request code
        Integer requestCode = postcard.getField(Integer.class, FIELD_REQUEST_CODE);

        // 是否限制Intent的packageName，限制后只会启动当前App内的页面，不启动其他App的页面，bool型
        boolean limitPackage = postcard.getBooleanField(FIELD_LIMIT_PACKAGE, false);

        // 设置package，先尝试启动App内的页面
        intent.setPackage(context.getPackageName());

        int r = startIntent(postcard, intent, context, requestCode, true);
        if (limitPackage || r == ResultCode.CODE_SUCCESS) {
            return r;
        }

        // App内启动失败，再尝试启动App外页面
        intent.setPackage(null);

        return startIntent(postcard, intent, context, requestCode, false);
    }

    /**
     * 启动Intent
     *
     * @param internal 是否启动App内页面
     */
    protected int startIntent(@NonNull Postcard postcard, @NonNull Intent intent,
                              Context context, Integer requestCode, boolean internal) {
        if (!checkIntent(context, intent)) {
            return ResultCode.CODE_NOT_FOUND;
        }

        if (startActivityByAction(postcard, intent, internal) == ResultCode.CODE_SUCCESS) {
            return ResultCode.CODE_SUCCESS;
        }

        return startActivityByDefault(postcard, context, intent, requestCode, internal);
    }

    /**
     * 检查Intent是否可跳转
     */
    protected boolean checkIntent(Context context, Intent intent) {
        if (mCheckIntentFirst) {
            try {
                PackageManager pm = context.getPackageManager();
                List list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                return list != null && list.size() > 0;
            } catch (Exception e) {
                // package manager has died
                RLogUtils.e(e);
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * 使用指定的 {@link StartActivityAction} 启动Intent
     *
     * @param internal 是否启动App内页面
     */
    protected int startActivityByAction(@NonNull Postcard postcard, @NonNull Intent intent, boolean internal) {
        try {
            final StartActivityAction action = postcard.getField(StartActivityAction.class, FIELD_START_ACTIVITY_ACTION);
            boolean result = action != null && action.startActivity(postcard, intent);

            if (result) {
                doAnimation(postcard);

                if (internal) {
                    postcard.putField(FIELD_STARTED_ACTIVITY, INTERNAL_ACTIVITY);
                    RLogUtils.i("internal activity started by StartActivityAction, postcard：", postcard);
                } else {
                    postcard.putField(FIELD_STARTED_ACTIVITY, EXTERNAL_ACTIVITY);
                    RLogUtils.i("external activity started by StartActivityAction, postcard：", postcard);
                }
                return ResultCode.CODE_SUCCESS;
            } else {
                return ResultCode.CODE_ERROR;
            }
        } catch (ActivityNotFoundException e) {
            RLogUtils.e(e);
            return ResultCode.CODE_NOT_FOUND;
        } catch (SecurityException e) {
            RLogUtils.e(e);
            return ResultCode.CODE_FORBIDDEN;
        }
    }


    /**
     * 使用默认方式启动Intent
     *
     * @param internal 是否启动App内页面
     */
    protected int startActivityByDefault(Postcard postcard, @NonNull Context context,
                                         @NonNull Intent intent, Integer requestCode, boolean internal) {
        try {
            Bundle options = postcard.getField(Bundle.class, FIELD_START_ACTIVITY_OPTIONS);

            if (requestCode != null && context instanceof Activity) {
                ActivityCompat.startActivityForResult((Activity) context, intent, requestCode,
                        options);
            } else {
                ActivityCompat.startActivity(context, intent, options);
            }
            doAnimation(postcard);

            if (internal) {
                postcard.putField(FIELD_STARTED_ACTIVITY, INTERNAL_ACTIVITY);
                RLogUtils.i("internal activity started, postcard：", postcard);
            } else {
                postcard.putField(FIELD_STARTED_ACTIVITY, EXTERNAL_ACTIVITY);
                RLogUtils.i("external activity started, postcard：", postcard);
            }

            return ResultCode.CODE_SUCCESS;
        } catch (ActivityNotFoundException e) {
            RLogUtils.e(e);
            return ResultCode.CODE_NOT_FOUND;
        } catch (SecurityException e) {
            RLogUtils.e(e);
            return ResultCode.CODE_FORBIDDEN;
        }
    }

    /**
     * 执行动画
     */
    protected void doAnimation(Postcard postcard) {
        Context context = postcard.getContext();
        int[] anim = postcard.getField(int[].class, FIELD_START_ACTIVITY_ANIMATION);
        if (context instanceof Activity && anim != null && anim.length == 2) {
            ((Activity) context).overridePendingTransition(anim[0], anim[1]);
        }
    }
}
