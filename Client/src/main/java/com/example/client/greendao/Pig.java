package com.example.client.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by alienware on 2018/1/28.
 */

@Entity
public class Pig {

    @Id
    private Long id;

    private String name;
}
