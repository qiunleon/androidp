package com.example.client.pattern.builder;

/**
 * Created by Qiu on 2017/12/11.
 */

public class Car {


    private String engine; // 发动机引擎

    private String transmission; // 变速箱

    private String  chassis; // 底盘

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getChassis() {
        return chassis;
    }

    public void setChassis(String chassis) {
        Person person = new Person.PersonBuilder("", 1).setEmail("").build();
        this.chassis = chassis;
    }

    @Override
    public String toString() {
        return "Car{" +
                "engine='" + engine + '\'' +
                ", transmission='" + transmission + '\'' +
                ", chassis='" + chassis + '\'' +
                '}';
    }
}