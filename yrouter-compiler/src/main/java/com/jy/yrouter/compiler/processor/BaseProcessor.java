package com.jy.yrouter.compiler.processor;

import com.jy.yrouter.annotation.common.Const;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

public abstract class BaseProcessor extends AbstractProcessor {

    protected Messager messager;
    protected Filer filer;
    protected Types types;
    protected Elements elements;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        types = processingEnvironment.getTypeUtils();
        elements = processingEnvironment.getElementUtils();

    }

    /**
     * 从字符串获取TypeElement对象
     */
    public TypeElement typeElement(String className) {
        return elements.getTypeElement(className);
    }

    /**
     * 从字符串获取TypeMirror对象
     */
    public TypeMirror typeMirror(String className) {
        return typeElement(className).asType();
    }

    /**
     * 从字符串获取ClassName对象
     */
    public ClassName className(String className) {
        return ClassName.get(typeElement(className));
    }

    public static String getClassName(TypeMirror typeMirror) {
        return typeMirror == null ? "" : typeMirror.toString();
    }

    public boolean isSubType(TypeMirror type, String className) {
        return type != null && types.isSubtype(type, typeMirror(className));
    }

    public boolean isSubType(Element element, String className) {
        return element != null && isSubType(element.asType(), className);
    }

    public boolean isSubType(Element element, TypeMirror typeMirror) {
        return element != null && types.isSubtype(element.asType(), typeMirror);
    }

    /**
     * 非抽象类
     */
    public boolean isConcreteType(Element element) {
        return element instanceof TypeElement && !element.getModifiers().contains(
                Modifier.ABSTRACT);
    }

    /**
     * 非抽象子类
     */
    public boolean isConcreteSubType(Element element, String className) {
        return isConcreteType(element) && isSubType(element, className);
    }

    /**
     * 非抽象子类
     */
    public boolean isConcreteSubType(Element element, TypeMirror typeMirror) {
        return isConcreteType(element) && isSubType(element, typeMirror);
    }

    public boolean isActivity(Element element) {
        return isConcreteSubType(element, Const.ACTIVITY_CLASS);
    }

    public boolean isFragment(Element element) {
        return isConcreteSubType(element, Const.FRAGMENT_CLASS);
    }

    public boolean isHandler(Element element) {
        return isConcreteSubType(element, Const.POSTCARD_HANDLER_CLASS);
    }

    public boolean isInterceptor(Element element) {
        return isConcreteSubType(element, Const.INTERCEPTOR_CLASS);
    }

    public static String randomHash() {
        return hash(UUID.randomUUID().toString());
    }

    public static String hash(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            return Integer.toHexString(str.hashCode());
        }
    }

    /**
     * 创建Handler。格式：<code>"com.demo.TestActivity"</code> 或 <code>new TestHandler()</code>
     */
    public CodeBlock buildHandler(boolean isActivity, Symbol.ClassSymbol cls) {
        CodeBlock.Builder b = CodeBlock.builder();
        if (isActivity) {
            b.add("$S", cls.className());
        } else {
            b.add("new $T()", cls);
        }
        return b.build();
    }

    /**
     * 创建Handler。格式： <code>new FragmentTransactionHandler("FragmentName")</code>
     */
    public CodeBlock buildFragmentHandler(Symbol.ClassSymbol cls) {
        CodeBlock.Builder b = CodeBlock.builder();
        b.add("new $T($S)", className(Const.FRAGMENT_HANDLER_CLASS), cls.className());
        return b.build();
    }

    /**
     * 创建Interceptors。格式：<code>, new Interceptor1(), new Interceptor2()</code>
     */
    public CodeBlock buildInterceptors(List<? extends TypeMirror> interceptors) {
        CodeBlock.Builder b = CodeBlock.builder();
        if (interceptors != null && interceptors.size() > 0) {
            for (TypeMirror type : interceptors) {
                if (type instanceof Type.ClassType) {
                    Symbol.TypeSymbol e = ((Type.ClassType) type).asElement();
                    if (e instanceof Symbol.ClassSymbol && isInterceptor(e)) {
                        b.add(", new $T()", e);
                    }
                }
            }
        }
        return b.build();
    }

    /**
     * 生成类似以下格式的类：
     * <pre>
     * public class UriAnnotationInit_454ff5f70a86b2c1b58b6cf7106b427e implements IUriAnnotationInit {
     *   public void init(UriAnnotationHandler handler) {
     *     handler.register("", "", "/jump_activity_1", "com.jy.yrouter.simple.TestBasicActivity", false);
     *   }
     * }
     * </pre>
     *
     * @param code             方法中的代码
     * @param genClassName     生成class的SimpleClassName，如：UriAnnotationInit_454ff5f70a86b2c1b58b6cf7106b427e
     * @param handlerClassName Handler类名，如：com.jy.yrouter.common.UriAnnotationHandler
     * @param interfaceName    接口名，如：com.jy.yrouter.common.IUriAnnotationInit
     * @param targetClassName  目标注解类，如：com.jy.yrouter.simple.TestBasicActivity
     */
    public void buildHandlerInitClass(CodeBlock code, String genClassName, String handlerClassName, String interfaceName, String targetClassName) {

        messager.printMessage(Diagnostic.Kind.NOTE, " --> create " + genClassName + "--from--" + targetClassName);

        MethodSpec methodSpec = MethodSpec.methodBuilder(Const.INIT_METHOD)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addParameter(className(handlerClassName), "handler")
                .addCode(code)
                .build();
        TypeSpec typeSpec = TypeSpec.classBuilder(genClassName)
                .addSuperinterface(className(interfaceName))
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodSpec)
                .build();
        try {
            JavaFile.builder(Const.GEN_PKG, typeSpec)
                    .build()
                    .writeTo(filer);
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.NOTE, " --> create " + genClassName + "--from--" + targetClassName + "error");
            throw new RuntimeException(e);
        }

        String fullImplName = Const.GEN_PKG + Const.DOT + genClassName;
        String className = "ServiceInit" + Const.SPLITTER + hash(genClassName);

        messager.printMessage(Diagnostic.Kind.NOTE, " --> create " + className + "--add--" + genClassName + "--to--ServiceLoader");
        new ServiceInitClassBuilder(className)
                .putDirectly(interfaceName, fullImplName, fullImplName, false)
                .build();
    }


    /**
     * 辅助工具类，用于生成ServiceInitClass，格式如下：
     * <pre>
     * public class ServiceInit_bd1485bc2e20898bb66dc4f4c90f11d3 {
     *   public static void init() {
     *     ServiceLoader.put(IUriAnnotationInit.class, "com.jy.yrouter.generated.UriAnnotationInit_454ff5f70a86b2c1b58b6cf7106b427e", com.jy.yrouter.generated.UriAnnotationInit_454ff5f70a86b2c1b58b6cf7106b427e.class, false);
     *   }
     * }
     * </pre>
     */
    public class ServiceInitClassBuilder {

        private final String className;
        private final CodeBlock.Builder builder;
        private final ClassName serviceLoaderClass;

        public ServiceInitClassBuilder(String className) {
            this.className = className;
            this.builder = CodeBlock.builder();
            this.serviceLoaderClass = className(Const.SERVICE_LOADER_CLASS);
        }

        public ServiceInitClassBuilder put(String interfaceName, String key, String implementName, boolean singleton) {
            builder.addStatement("$T.put($T.class, $S, $T.class, $L)",
                    serviceLoaderClass,
                    className(interfaceName),
                    key,
                    className(implementName),
                    singleton);
            return this;
        }

        public ServiceInitClassBuilder putDirectly(String interfaceName, String key, String implementName, boolean singleton) {
            // implementName是注解生成的类，直接用$L拼接原始字符串
            builder.addStatement("$T.put($T.class, $S, $L.class, $L)",
                    serviceLoaderClass,
                    className(interfaceName),
                    key,
                    implementName,
                    singleton);
            return this;
        }

        public void build() {
            MethodSpec methodSpec = MethodSpec.methodBuilder(Const.INIT_METHOD)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(TypeName.VOID)
                    .addCode(this.builder.build())
                    .build();

            TypeSpec typeSpec = TypeSpec.classBuilder(this.className)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(methodSpec)
                    .build();
            try {
                JavaFile.builder(Const.GEN_PKG_SERVICE, typeSpec)
                        .build()
                        .writeTo(filer);
            } catch (IOException e) {
                messager.printMessage(Diagnostic.Kind.NOTE, " --> create " + this.className + "error");
                throw new RuntimeException(e);
            }
        }
    }
}
