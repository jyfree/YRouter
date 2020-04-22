package com.jy.yrouter.common;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.jy.yrouter.activity.StartActivityAction;
import com.jy.yrouter.components.ActivityLauncher;
import com.jy.yrouter.core.Postcard;
import com.jy.yrouter.utils.RLogUtils;

import java.util.HashMap;

/**
 * @description 继承DefaultPostcard，用于从fragment跳转
 * @date: 2020/4/21 17:20
 * @author: jy
 */
public class FragmentPostcard extends DefaultPostcard {

    public FragmentPostcard(@NonNull Fragment fragment, @NonNull Uri uri) {
        super(fragment.getContext(), uri);
        configStartAction(fragment);
    }

    public FragmentPostcard(@NonNull Fragment fragment, @NonNull String uri) {
        super(fragment.getContext(), uri);
        configStartAction(fragment);
    }

    public FragmentPostcard(@NonNull Fragment fragment, @NonNull String uri, HashMap<String, Object> extra) {
        super(fragment.getContext(), uri, extra);
        configStartAction(fragment);
    }

    private void configStartAction(@NonNull Fragment fragment) {
        putField(ActivityLauncher.FIELD_START_ACTIVITY_ACTION, new FragmentStartActivityAction(fragment));
    }

    private static class FragmentStartActivityAction implements StartActivityAction {
        private Fragment fragment;

        public FragmentStartActivityAction(@NonNull Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public boolean startActivity(@NonNull Postcard postcard, @NonNull Intent intent)
                throws ActivityNotFoundException, SecurityException {
            try {
                Bundle options = postcard.getField(Bundle.class, ActivityLauncher.FIELD_START_ACTIVITY_OPTIONS);

                Integer requestCode = postcard.getField(Integer.class, ActivityLauncher.FIELD_REQUEST_CODE);

                if (requestCode != null) {
                    fragment.startActivityForResult(intent, requestCode, options);
                } else {
                    fragment.startActivity(intent, options);
                }
                return true;
            } catch (ActivityNotFoundException e) {
                RLogUtils.e(e);
                return false;
            } catch (SecurityException e) {
                RLogUtils.e(e);
                return false;
            }
        }
    }
}
