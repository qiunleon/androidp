package com.example.client.pattern.singleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Qiu on 2017/12/8.
 */

public class Vector_Singleton {

    private static Map<String, Object> objectsMap = new HashMap<>();

    private Vector_Singleton() {

    }

    public static void registerService(String key, Object instance) {
        if (!objectsMap.containsKey(key)) {
            objectsMap.put(key, instance);
        }
    }

    public static Object getInstance(String key) {
        return objectsMap.containsKey(key);
    }
}
