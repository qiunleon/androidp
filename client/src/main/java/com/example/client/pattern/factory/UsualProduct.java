package com.example.client.pattern.factory;

import com.example.client.util.LogUtils;

/**
 * Created by Qiu on 2017/12/12.
 */

public class UsualProduct extends Product {

    @Override
    public void introduction() {
        LogUtils.d(this);
    }

    @Override
    public String toString() {
        return "UsualProduct{}";
    }
}
