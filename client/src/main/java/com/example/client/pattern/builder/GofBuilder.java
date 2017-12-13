package com.example.client.pattern.builder;

/**
 * Created by Qiu on 2017/12/11.
 */

public class GofBuilder extends Builder {

    private Car car = new Car();

    @Override
    public void buildEngine(String engine) {
        car.setEngine(engine);
    }

    @Override
    public void buildTransmission(String transmission) {
        car.setTransmission(transmission);
    }

    @Override
    public void buildChassis(String chassis) {
        car.setChassis(chassis);
    }

    @Override
    public Car create() {
        return car;
    }
}
