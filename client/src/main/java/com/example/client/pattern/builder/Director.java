package com.example.client.pattern.builder;

/**
 * Created by Qiu on 2017/12/11.
 */

public class Director {

        Builder builder = null;

        public Director(Builder builder) {
            this.builder = builder;
        }

        public Car createCar(String engine, String transmission, String chassis){
            this.builder.buildChassis(chassis);
            this.builder.buildEngine(engine);
            this.builder.buildTransmission(transmission);
            return builder.create();
        }

        public static void main(String[] args) {
            Builder builder = new GofBuilder();
            Director director = new Director(builder);
            Car myCar = director.createCar("A", "B", "C");
            System.out.println(myCar.toString());
        }
    }
