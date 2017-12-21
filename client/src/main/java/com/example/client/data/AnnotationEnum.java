package com.example.client.data;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by alienware on 2017/12/14.
 */

public class AnnotationEnum {

    public static final int STATE_NONE = -1;
    public static final int STATE_LOADING = 0;
    public static final int STATE_SUCCESS = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_EMPTY = 3;

    private @State int state;

    public void setState(@State int state){
        this.state = state;
    }

    @State
    public int getState() {
        return this.state;
    }

    @IntDef({STATE_EMPTY, STATE_ERROR, STATE_LOADING, STATE_NONE, STATE_SUCCESS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }
}
