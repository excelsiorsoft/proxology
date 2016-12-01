package com.excelsiorsoft.proxology.proxies;

import com.excelsiorsoft.proxology.handlers.MethodCallInterceptor;
import com.excelsiorsoft.proxology.handlers.MethodInterpreters;
import com.excelsiorsoft.proxology.handlers.PropertyValueStore;
import com.excelsiorsoft.proxology.utils.EqualisableByState;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.stream.Stream;

import static com.excelsiorsoft.proxology.handlers.MethodInterpreters.*;

public final class Proxies {

    @SuppressWarnings("unchecked")
    public static <T> T simpleProxy(Class<? extends T> iface, InvocationHandler handler, Class<?>...otherIfaces) {
        Class<?>[] allInterfaces = Stream.concat(
                Stream.of(iface),
                Stream.of(otherIfaces))
                .distinct()
                .toArray(Class<?>[]::new);

        return (T) Proxy.newProxyInstance(iface.getClassLoader(),
                allInterfaces,
                handler);
    }

    public static <T> T interceptingProxy(T target, Class<T> iface, MethodCallInterceptor interceptor) {
        return simpleProxy(iface,
                caching(intercepting(
                        handlingDefaultMethods(MethodInterpreters.binding(target)),
                        interceptor)));
    }

    public static <T> T propertyMapping(Class<? extends T> iface, Map<String, Object> propertyValues) {
        PropertyValueStore store = new PropertyValueStore(iface, propertyValues);
        return simpleProxy(iface, store.createMethodInterpreter(), EqualisableByState.class);
    }

}
