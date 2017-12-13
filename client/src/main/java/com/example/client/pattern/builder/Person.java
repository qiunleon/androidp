package com.example.client.pattern.builder;

/**
 * Created by Qiu on 2017/12/11.
 */

public class Person {

    private String name;
    private int age;
    private String mobile;
    private String email;

    private Person(PersonBuilder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.mobile = builder.mobile;
        this.email = builder.email;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }


    public static class PersonBuilder {

        private String name;
        private int age;
        private String mobile;
        private String email;

        public PersonBuilder(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public PersonBuilder setMobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public PersonBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Person build() {
            Person person = new Person(this);
            if (person.getAge() > 120) {
                throw new IllegalStateException("Age out of range"); //线程安全
            }
            return person;
        }
    }
}
