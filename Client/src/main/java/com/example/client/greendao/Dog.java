package com.example.client.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Dog {
    @Id Long id;

    private String name;

    private long dogUserId;

    private String dogAlias;

    @Generated(hash = 582171981)
    public Dog(Long id, String name, long dogUserId, String dogAlias) {
        this.id = id;
        this.name = name;
        this.dogUserId = dogUserId;
        this.dogAlias = dogAlias;
    }

    @Generated(hash = 2001499333)
    public Dog() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserId() {
        return this.dogUserId;
    }

    public void setUserId(long dogUserId) {
        this.dogUserId = dogUserId;
    }

    public String getDogAlias() {
        return this.dogAlias;
    }

    public void setDogAlias(String dogAlias) {
        this.dogAlias = dogAlias;
    }
}
