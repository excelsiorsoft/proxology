package com.excelsiorsoft.proxology.handlers.early;

import com.excelsiorsoft.proxology.handlers.MethodCallHandler;

@FunctionalInterface
public interface UnboundMethodCallHandler<S> {
    MethodCallHandler bind(S state);
}
