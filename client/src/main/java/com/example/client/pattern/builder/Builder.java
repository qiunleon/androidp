package com.example.client.pattern.builder;

/**
 * Created by Qiu on 2017/12/11.
 */

public abstract class Builder {

    public abstract void buildEngine(String engine);
    public abstract void buildTransmission(String transmission);
    public abstract void buildChassis(String chassis);

    public abstract Car create();
}