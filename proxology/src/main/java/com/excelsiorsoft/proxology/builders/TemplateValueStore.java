package com.excelsiorsoft.proxology.builders;

import com.excelsiorsoft.proxology.handlers.MethodInterpreter;
import com.excelsiorsoft.proxology.proxies.Proxies;
import com.excelsiorsoft.proxology.reflection.TypeInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.excelsiorsoft.proxology.handlers.MethodInterpreters.binding;
import static com.excelsiorsoft.proxology.handlers.MethodInterpreters.handlingDefaultMethods;

public final class TemplateValueStore<V, B extends Supplier<V>, T extends Template<V, B>> implements Supplier<V> {

    private final Class<T> templateClass;
    private final Map<String, Object> values = new HashMap<>();

    public TemplateValueStore(Class<T> templateClass) {
        this.templateClass = templateClass;
    }

    public static <V, B extends Supplier<V>, T extends Template<V, B>> B createBuilder(Class<T> templateClass) {
        TemplateValueStore<V, B, T> valueStore = new TemplateValueStore<>(templateClass);
        Class<B> builderClass = TypeInfo.forType(templateClass).getInterface(Template.class).getSecondTypeArgument().getRawType();

        return Proxies.simpleProxy(builderClass, valueStore.getMethodInterpreter(builderClass));
    }

    public MethodInterpreter getMethodInterpreter(Class<B> builderClass) {
        return handlingDefaultMethods(binding(
                this,
                BuilderClassInterpreter.interpret(builderClass).bind(values)));
    }

    public V get() {
        return Proxies.simpleProxy(
                templateClass,
                handlingDefaultMethods(
                        TemplateClassInterpreter.interpret(templateClass).bind(values)
                )
        ).get();
    }

}
