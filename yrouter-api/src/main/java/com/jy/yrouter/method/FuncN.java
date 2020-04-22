package com.jy.yrouter.method;

public interface FuncN<R> extends Function {
    R call(Object... args);
}
