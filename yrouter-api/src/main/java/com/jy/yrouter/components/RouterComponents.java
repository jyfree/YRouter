package com.jy.yrouter.components;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.jy.yrouter.core.Postcard;
import com.jy.yrouter.core.PostcardHandler;
import com.jy.yrouter.service.DefaultFactory;
import com.jy.yrouter.service.IFactory;

public class RouterComponents {

    @NonNull
    private static AnnotationLoader sAnnotationLoader = DefaultAnnotationLoader.INSTANCE;

    @NonNull
    private static ActivityLauncher sActivityLauncher = DefaultActivityLauncher.INSTANCE;

    @NonNull
    private static IFactory sDefaultFactory = DefaultFactory.INSTANCE;

    public static void setAnnotationLoader(AnnotationLoader loader) {
        sAnnotationLoader = loader == null ? DefaultAnnotationLoader.INSTANCE : loader;
    }

    public static void setActivityLauncher(ActivityLauncher launcher) {
        sActivityLauncher = launcher == null ? DefaultActivityLauncher.INSTANCE : launcher;
    }

    public static void setDefaultFactory(IFactory factory) {
        sDefaultFactory = factory == null ? DefaultFactory.INSTANCE : factory;
    }

    @NonNull
    public static IFactory getDefaultFactory() {
        return sDefaultFactory;
    }

    /**
     * @see AnnotationLoader#load(PostcardHandler, Class)
     */
    public static <T extends PostcardHandler> void loadAnnotation(T handler, Class<? extends AnnotationInit<T>> initClass) {
        sAnnotationLoader.load(handler, initClass);
    }

    /**
     * @see ActivityLauncher#startActivity(Postcard, Intent)
     */
    public static int startActivity(@NonNull Postcard postcard, @NonNull Intent intent) {
        return sActivityLauncher.startActivity(postcard, intent);
    }
}
