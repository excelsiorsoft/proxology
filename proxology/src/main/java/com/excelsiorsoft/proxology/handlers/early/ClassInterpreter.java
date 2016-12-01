package com.excelsiorsoft.proxology.handlers.early;

import com.excelsiorsoft.proxology.memoization.Memoizer;
import com.excelsiorsoft.proxology.reflection.MethodInfo;
import com.excelsiorsoft.proxology.reflection.TypeInfo;

import java.util.function.Function;
import java.util.stream.Collectors;

@FunctionalInterface
public interface ClassInterpreter<T> {

    static <T> ClassInterpreter<T> cached(ClassInterpreter<T> interpreter) {
        return Memoizer.memoize(interpreter::interpret)::apply;
    }

    static <T> ClassInterpreter<T> mappingWith(UnboundMethodInterpreter<T> interpreter) {
        return iface -> TypeInfo.forType(iface)
                .streamNonDefaultPublicInstanceMethods()
                .map(MethodInfo::getMethod)
                .collect(Collectors.toMap(
                        Function.identity(),
                        interpreter::interpret
                ))::get;
    }

    UnboundMethodInterpreter<T> interpret(Class<?> iface);

}
