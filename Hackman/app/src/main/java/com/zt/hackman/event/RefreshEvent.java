package com.zt.hackman.event;

/**
 * Created by Administrator on 2016/9/27.
 */
public class RefreshEvent  {

    public static final int TYPE_REFRESH_PERSONAL_CENTER_ACTIVITY = 2000;
    public static final int TYPE_LOGIN_REFRESH = 200;
    public static final int TYPE_LOGIN_CANCEL = 300;
    public static final int TYPE_AFFIRM_PICK_REFRESH =400;  //待取货刷新
    public static final int TYPE_ARRIVE_REFRESH =500;  //待送达刷新

    public int type;

    public RefreshEvent(int type){
        this.type = type;
    }
}
