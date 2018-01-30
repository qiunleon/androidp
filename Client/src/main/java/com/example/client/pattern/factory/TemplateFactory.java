package com.example.client.pattern.factory;

/**
 * Created by Qiu on 2017/12/12.
 */

public abstract class TemplateFactory {

    public abstract <T extends Product> T createProduct(Class<T> clz);

}
