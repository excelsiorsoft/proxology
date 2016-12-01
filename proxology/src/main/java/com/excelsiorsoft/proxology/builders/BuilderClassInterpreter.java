package com.excelsiorsoft.proxology.builders;

import com.excelsiorsoft.proxology.handlers.early.ClassInterpreter;
import com.excelsiorsoft.proxology.handlers.early.UnboundMethodCallHandler;
import com.excelsiorsoft.proxology.handlers.early.UnboundMethodInterpreter;
import com.excelsiorsoft.proxology.reflection.MethodInfo;

import java.lang.reflect.Method;
import java.util.Map;

public final class BuilderClassInterpreter {
    private static final ClassInterpreter<Map<String, Object>> cache =
            ClassInterpreter.cached(
                    ClassInterpreter.mappingWith(BuilderClassInterpreter::interpret));

    public static UnboundMethodInterpreter<Map<String, Object>> interpret(Class<?> templateClass) {
        return cache.interpret(templateClass);
    }

    private static UnboundMethodCallHandler<Map<String, Object>> interpret(Method method) {
        String propertyName = MethodInfo.forMethod(method).getPropertyName();
        return state -> (proxy, args) -> {
            state.put(propertyName, args[0]);
            return proxy;
        };
    }

}
