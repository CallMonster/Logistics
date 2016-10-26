package com.zt.hackman.event;

/**
 * Created by Administrator on 2016/9/20.
 */
public class RegisterEvent {
    public static final int TYPE_GO_HACK = 1;
    public static final int TYPE_GO_MAIN = 2;
    public int type;

    public RegisterEvent(int type){
        this.type = type;
    }
}
