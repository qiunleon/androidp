package com.example.client.pattern.servicelocator;

import java.util.HashMap;
import java.util.Map;

public class AvServiceLocator {

    private static AvServiceLocator avServiceLocator;
    private static Map<String, EvService> maps = new HashMap<>();

    private AvServiceLocator() {

    }

    public static AvServiceLocator getInstance() {
        if (avServiceLocator == null) {
            synchronized (AvServiceLocator.class) {
                if (avServiceLocator == null) {
                    avServiceLocator = new AvServiceLocator();
                }
            }
        }
        return avServiceLocator;
    }

    public void register(String code, EvService evService) {
        System.out.println("code: " + code);
        if (!maps.containsKey(code)) {
            maps.put(code, evService);
        }
    }

    public EvService getService(String code) {
        if (maps.containsKey(code)) {
            return maps.get(code);
        }
        return null;
    }

    public void showCacheService() {
//        for (String s : maps.keySet()) {
//            System.out.println(this.getClass().getSimpleName() + " show cache service: " + maps.get(s));
//        }
        for (Map.Entry<String, EvService> entry : maps.entrySet()) {
            System.out.println(this.getClass().getSimpleName() + "show cache service, code: " + entry.getKey() + ", service: " + entry.getValue());
        }
    }

    public void init() {
        new DefaultEvServiceImpl();
        new EvServiceImplA();
        new EvServiceImplB();
        System.out.println(this.getClass().getSimpleName() + " init.");
    }
}
