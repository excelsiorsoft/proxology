package com.excelsiorsoft.proxology.handlers.early;

import com.excelsiorsoft.proxology.handlers.MethodInterpreter;

import java.lang.reflect.Method;

@FunctionalInterface
public interface UnboundMethodInterpreter<S> {

    UnboundMethodCallHandler<S> interpret(Method method);

    default MethodInterpreter bind(S state) {
        return method -> interpret(method).bind(state);
    }
}
