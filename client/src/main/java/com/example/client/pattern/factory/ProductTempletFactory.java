package com.example.client.pattern.factory;

/**
 * Created by Qiu on 2017/12/12.
 */

public class ProductTempletFactory extends TempletFactory {

    public static void main(String[] args) {
        TempletFactory factory = new ProductTempletFactory();
        Product product = factory.createProduct(Product.class);
        product.introduction();
    }

    @Override
    public <T extends Product> T createProduct(Class<T> clz) {
        Product p = null;
        try {
            p = (Product) Class.forName(clz.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) p;
    }
}
