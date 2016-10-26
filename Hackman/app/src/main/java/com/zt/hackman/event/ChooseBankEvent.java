package com.zt.hackman.event;

/**
 * Created by Administrator on 2016/9/26.
 */
public class ChooseBankEvent {

    public static final int TYPE_CHOOSE_BANK = 3;
    public int type;
    public int index;

    public ChooseBankEvent(int type,int index){
        this.type = type;
        this.index = index;
    }
}
