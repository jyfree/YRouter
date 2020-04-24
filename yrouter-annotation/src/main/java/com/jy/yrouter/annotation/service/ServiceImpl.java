package com.jy.yrouter.annotation.service;

/**
 * @description Service的一个实现
 * @date: 2020/4/21 11:35
 * @author: jy
 */
public class ServiceImpl {

    public static final String SPLITTER = ":";
    public static final String SINGLETON = "singleton";
    public static final String DEFAULT_IMPL_KEY = "_service_default_impl";

    public static String checkConflict(String interfaceName, ServiceImpl impl,
                                       ServiceImpl previous) {
        if (impl != null && previous != null && !stringEquals(previous.implementation,
                impl.implementation)) {
            if (DEFAULT_IMPL_KEY.equals(impl.getKey())) {
                //接口interfaceName的默认实现只允许存在一个,目前存在多个默认实现: previous, impl
                return String.format("Interface %s Only one default implementation is allowed \n There are currently multiple default implementations: %s, %s",
                        interfaceName, previous, impl);
            } else {
                //接口interfaceName 对应key=impl.getKey() 存在多个实现: previous, impl
                return String.format("Interface %s Corresponding key='%s' Multiple implementations exist: %s, %s",
                        interfaceName, impl.getKey(), previous, impl);
            }

        }
        return null;
    }

    @SuppressWarnings("StringEquality")
    private static boolean stringEquals(String s1, String s2) {
        return s1 == s2 || s1 != null && s1.equals(s2);
    }

    private static boolean isEmpty(String key) {
        return key == null || key.length() == 0;
    }

    private final String key;
    private final String implementation;
    private final Class implementationClazz;
    private final boolean singleton;

    public ServiceImpl(String key, Class implementation, boolean singleton) {
        if (key == null || implementation == null) {
            throw new RuntimeException("key and implementation Should not be empty");
        }
        this.key = key;
        this.implementation = "";
        this.implementationClazz = implementation;
        this.singleton = singleton;
    }

    public ServiceImpl(String key, String implementation, boolean singleton) {
        if (isEmpty(implementation)) {
            throw new RuntimeException("implementation Should not be empty");
        }
        this.key = isEmpty(key) ? implementation : key; // 没有指定key，则为implementation
        this.implementation = implementation;
        this.implementationClazz = null;
        this.singleton = singleton;
    }

    public String toConfig() {
        String s = key + SPLITTER + implementation;
        if (singleton) {
            s += SPLITTER + SINGLETON;
        }
        return s;
    }

    /**
     * not null
     */
    public String getKey() {
        return key;
    }

    public String getImplementation() {
        return implementation;
    }

    public Class getImplementationClazz() {
        return implementationClazz;
    }

    public boolean isSingleton() {
        return singleton;
    }

    @Override
    public String toString() {
        return implementation;
    }
}
