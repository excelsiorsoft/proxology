package com.excelsiorsoft.proxology.handlers;

@FunctionalInterface
public interface MethodCallHandler {
    Object invoke(Object proxy, Object[] args) throws Throwable;
}
