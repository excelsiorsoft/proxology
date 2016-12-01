package com.excelsiorsoft.proxology.beans;

import com.excelsiorsoft.proxology.proxies.Proxies;
import com.excelsiorsoft.proxology.utils.EqualisableByState;

public final class BeanProxy {

    private BeanProxy() {
    }

    public static <T> T proxying(Class<T> proxyClass) {
        BeanProxySchema schema = BeanProxySchema.forClass(proxyClass);
        BeanProxyStorage storage = schema.createStorage();

        return Proxies.simpleProxy(proxyClass, storage.getMethodInterpreter(), EqualisableByState.class);
    }

}
