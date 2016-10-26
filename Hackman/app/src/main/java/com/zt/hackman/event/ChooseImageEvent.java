package com.zt.hackman.event;

/**
 * Created by Administrator on 2016/9/22.
 */
public class ChooseImageEvent {
    public static final int TYPE_CHOOSE_IMAGE = 1;
    public int type;
    public ChooseImageEvent(int type){
        this.type = type;
    }
}
