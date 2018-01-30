package com.example.client.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by alienware on 2018/1/28.
 */

@Entity
public class Pig {

    @Id
    private Long id;

    private String name;

    @Generated(hash = 534441060)
    public Pig(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 220568114)
    public Pig() {
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
}
