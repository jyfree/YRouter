package com.jy.yrouter.compiler.processor;

import com.google.auto.service.AutoService;
import com.jy.yrouter.annotation.RouterService;
import com.jy.yrouter.annotation.common.Const;
import com.jy.yrouter.annotation.service.ServiceImpl;
import com.sun.tools.javac.code.Symbol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
public class ServiceAnnotationProcessor extends BaseProcessor {

    /**
     * interfaceClass --> Entity
     */
    private final HashMap<String, Entity> mEntityMap = new HashMap<>();
    private String mHash = null;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        if (env.processingOver()) {
            generateInitClass();
        } else {
            messager.printMessage(Diagnostic.Kind.NOTE, "ServiceAnnotationProcessor--processing...");
            processAnnotations(env);
            messager.printMessage(Diagnostic.Kind.NOTE, "ServiceAnnotationProcessor--finish...");
            messager.printMessage(Diagnostic.Kind.NOTE, "...");
            messager.printMessage(Diagnostic.Kind.NOTE, "...");
        }
        return true;
    }

    private void processAnnotations(RoundEnvironment env) {
        for (Element element : env.getElementsAnnotatedWith(RouterService.class)) {
            if (!(element instanceof Symbol.ClassSymbol)) {
                continue;
            }

            Symbol.ClassSymbol cls = (Symbol.ClassSymbol) element;
            if (mHash == null) {
                mHash = hash(cls.className());
            }

            RouterService service = cls.getAnnotation(RouterService.class);
            if (service == null) {
                continue;
            }

            List<? extends TypeMirror> typeMirrors = getInterface(service);
            String[] keys = service.key();

            String implementationName = cls.className();
            boolean singleton = service.singleton();
            final boolean defaultImpl = service.defaultImpl();

            messager.printMessage(Diagnostic.Kind.NOTE, " --> prepare--from--" + implementationName);

            if (typeMirrors != null && !typeMirrors.isEmpty()) {
                for (TypeMirror mirror : typeMirrors) {
                    if (mirror == null) {
                        continue;
                    }
                    if (!isConcreteSubType(cls, mirror)) {
                        //没有实现注解RouterService标注的接口
                        String msg = cls.className() + "No implementation annotation" + RouterService.class.getName()
                                + "Labeled interface" + mirror.toString();
                        throw new RuntimeException(msg);
                    }
                    String interfaceName = getClassName(mirror);

                    Entity entity = mEntityMap.get(interfaceName);
                    if (entity == null) {
                        entity = new Entity(interfaceName);
                        mEntityMap.put(interfaceName, entity);
                    }

                    if (defaultImpl) {
                        //如果设置为默认实现，则手动添加一个内部标识默认实现的key
                        entity.put(ServiceImpl.DEFAULT_IMPL_KEY, implementationName, singleton);
                    }

                    if (keys.length > 0) {
                        for (String key : keys) {
                            if (key.contains(":")) {
                                //实现类注解RouterService的key参数不可包含冒号
                                String msg = String.format("%s: annotation %s Key parameter cannot contain colon",
                                        implementationName, RouterService.class.getName());
                                throw new RuntimeException(msg);
                            }
                            entity.put(key, implementationName, singleton);
                        }
                    } else {
                        entity.put(null, implementationName, singleton);
                    }
                }
            }
        }
    }

    private void generateInitClass() {
        messager.printMessage(Diagnostic.Kind.NOTE, " --> ServiceAnnotationProcessor--generate--init--Class--start");

        if (mEntityMap.isEmpty() || mHash == null) {
            return;
        }

        ServiceInitClassBuilder generator = new ServiceInitClassBuilder("ServiceInit" + Const.SPLITTER + mHash);
        for (Map.Entry<String, Entity> entry : mEntityMap.entrySet()) {
            for (ServiceImpl service : entry.getValue().getMap().values()) {
                generator.put(entry.getKey(), service.getKey(), service.getImplementation(), service.isSingleton());
            }
        }
        generator.build();

        messager.printMessage(Diagnostic.Kind.NOTE, " --> ServiceAnnotationProcessor--generate--init--Class--end");
        messager.printMessage(Diagnostic.Kind.NOTE, "...");
        messager.printMessage(Diagnostic.Kind.NOTE, "...");
    }

    private static List<? extends TypeMirror> getInterface(RouterService service) {
        try {
            service.interfaces();
        } catch (MirroredTypesException mte) {
            return mte.getTypeMirrors();
        }
        return null;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Collections.singletonList(RouterService.class.getName()));
    }

    public static class Entity {

        private final String mInterfaceName;

        private final Map<String, ServiceImpl> mMap = new HashMap<>();

        public Entity(String interfaceName) {
            mInterfaceName = interfaceName;
        }

        public Map<String, ServiceImpl> getMap() {
            return mMap;
        }

        public void put(String key, String implementationName, boolean singleton) {
            if (implementationName == null) {
                return;
            }
            ServiceImpl impl = new ServiceImpl(key, implementationName, singleton);
            ServiceImpl prev = mMap.put(impl.getKey(), impl);
            String errorMsg = ServiceImpl.checkConflict(mInterfaceName, prev, impl);
            if (errorMsg != null) {
                throw new RuntimeException(errorMsg);
            }
        }

        public List<String> getContents() {
            List<String> list = new ArrayList<>();
            for (ServiceImpl impl : mMap.values()) {
                list.add(impl.toConfig());
            }
            return list;
        }
    }
}
