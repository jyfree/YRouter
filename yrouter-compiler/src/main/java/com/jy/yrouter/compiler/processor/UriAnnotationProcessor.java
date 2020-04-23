package com.jy.yrouter.compiler.processor;

import com.google.auto.service.AutoService;
import com.jy.yrouter.annotation.RouterUri;
import com.jy.yrouter.annotation.common.Const;
import com.squareup.javapoet.CodeBlock;
import com.sun.tools.javac.code.Symbol;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class UriAnnotationProcessor extends BaseProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        if (annotations == null || annotations.isEmpty()) {
            return false;
        }
        messager.printMessage(Diagnostic.Kind.NOTE, "UriAnnotationProcessor--processing...");

        CodeBlock.Builder builder = CodeBlock.builder();
        String hash = null;
        String name = null;
        for (Element element : env.getElementsAnnotatedWith(RouterUri.class)) {
            if (!(element instanceof Symbol.ClassSymbol)) {
                continue;
            }
            boolean isActivity = isActivity(element);
            boolean isHandler = isHandler(element);

            if (!isActivity && !isHandler) {
                continue;
            }

            Symbol.ClassSymbol cls = (Symbol.ClassSymbol) element;
            RouterUri uri = cls.getAnnotation(RouterUri.class);
            if (uri == null) {
                continue;
            }

            if (hash == null) {
                name = cls.className();
                hash = hash(name);
            }
            CodeBlock handler = buildHandler(isActivity, cls);
            CodeBlock interceptors = buildInterceptors(getInterceptors(uri));

            // scheme, host, path, handler, exported, interceptors
            String[] pathList = uri.path();
            for (String path : pathList) {
                builder.addStatement("handler.register($S, $S, $S, $L, $L$L)",
                        uri.scheme(),
                        uri.host(),
                        path,
                        handler,
                        uri.exported(),
                        interceptors);
            }
        }
        if (hash == null) {
            hash = randomHash();
            name = hash;
        }
        buildHandlerInitClass(builder.build(), "UriAnnotationInit" + Const.SPLITTER + hash,
                Const.URI_ANNOTATION_HANDLER_CLASS, Const.URI_ANNOTATION_INIT_CLASS, name);

        messager.printMessage(Diagnostic.Kind.NOTE, "UriAnnotationProcessor--finish...");
        return true;
    }


    private static List<? extends TypeMirror> getInterceptors(RouterUri scheme) {
        try {
            scheme.interceptors();
        } catch (MirroredTypesException mte) {
            return mte.getTypeMirrors();
        }
        return null;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Collections.singletonList(RouterUri.class.getName()));
    }
}
