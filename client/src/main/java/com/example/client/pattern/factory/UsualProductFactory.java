package com.example.client.pattern.factory;

/**
 * Created by Qiu on 2017/12/12.
 */

public class UsualProductFactory extends UsualFactory {

    @Override
    public Product createProduct() {
        return new UsualProduct();
    }

    public static void main(String[] args ){
        UsualProductFactory factory = new UsualProductFactory();
        Product product = factory.createProduct();
        product.introduction();
    }
}

