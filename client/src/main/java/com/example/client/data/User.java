package com.example.client.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

/**
 @ Entity 定义实体
 @ nameInDb 在数据库中的名字，如不写则为实体中类名
 @ indexes 索引
 @ createInDb 是否创建表，默认为true,false时不创建
 @ schema 指定架构名称为实体
 @ active 无论是更新生成都刷新

 @ Id
 @ NotNull 不为null
 @ Unique 唯一约束
 @ ToMany 一对多
 @ OrderBy 排序
 @ ToOne 一对一
 @ Transient 不存储在数据库中
 @ generated 由greendao产生的构造函数或方法
 * Created by Qiu on 2017/12/14.
 */

@Entity
public class User {
    @Id
    private Long id;

    private String name;
    @Transient
    private int tempUsageCount; // not persisted

    @Generated(hash = 873297011)
    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 586692638)
    public User() {
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
