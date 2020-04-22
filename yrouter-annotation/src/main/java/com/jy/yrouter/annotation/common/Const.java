package com.jy.yrouter.annotation.common;

public class Const {

    public static final String NAME = "YRouter";
    public static final String PKG = "com.jy.yrouter.";

    // 生成的代码
    public static final String GEN_PKG = PKG + "generated";
    public static final String GEN_PKG_SERVICE = GEN_PKG + ".service";

    public static final String SPLITTER = "_";
    /**
     * ServiceLoader初始化
     */
    public static final String SERVICE_LOADER_INIT = GEN_PKG + ".ServiceLoaderInit";

    public static final char DOT = '.';

    public static final String INIT_METHOD = "init";


    // Library中的类名
    public static final String PAGE_ANNOTATION_HANDLER_CLASS =
            PKG + "common.PageAnnotationHandler";
    public static final String PAGE_ANNOTATION_INIT_CLASS =
            PKG + "common.IPageAnnotationInit";
    public static final String URI_ANNOTATION_HANDLER_CLASS =
            PKG + "common.UriAnnotationHandler";
    public static final String URI_ANNOTATION_INIT_CLASS =
            PKG + "common.IUriAnnotationInit";

    public static final String POSTCARD_HANDLER_CLASS =
            PKG + "core.PostcardHandler";
    public static final String INTERCEPTOR_CLASS =
            PKG + "core.Interceptor";
    public static final String SERVICE_LOADER_CLASS =
            PKG + "service.ServiceLoader";

    public static final String FRAGMENT_HANDLER_CLASS =
            PKG + "fragment.FragmentTransactionHandler";


    // Android中的类名
    public static final String ACTIVITY_CLASS = "android.app.Activity";
    // Android中的类名
    public static final String FRAGMENT_CLASS = "androidx.fragment.app.Fragment";
}
