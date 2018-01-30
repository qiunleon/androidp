package com.example.client.pattern.servicelocator;

public class EvServiceLocator {
    private static EvServiceLocator sServiceLocator;

    private EvServiceLocator() {

    }

    public static EvServiceLocator getInstance() {
        if (sServiceLocator == null) {
            synchronized (EvServiceLocator.class) {
                if (sServiceLocator == null) {
                    sServiceLocator = new EvServiceLocator();
                }
            }
        }
        return sServiceLocator;
    }

    public EvService getService(String serviceName) {
        EvService evService;
        if ("serviceImpla".equals(serviceName)) {
            evService = new EvServiceImplA();
        } else if ("serviceImplb".equals(serviceName)) {
            evService = new EvServiceImplB();
        } else {
            evService = null;
        }
        return evService;
    }
}
