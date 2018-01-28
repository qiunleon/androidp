package com.example.client.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by alienware on 2018/1/28.
 */

@Entity
public class Dog {
    @Id Long id;

    private String name;

    private long userId;

    private String dogAlias;
}
