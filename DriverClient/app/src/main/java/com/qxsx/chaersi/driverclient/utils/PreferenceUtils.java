package com.qxsx.chaersi.driverclient.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Chaersi on 16/7/7.
 */
public class PreferenceUtils {

    private final String SAVE_USER_KEY="USER_KEY";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public PreferenceUtils(Context context){
        preferences = context.getSharedPreferences(SAVE_USER_KEY, Context.MODE_PRIVATE);
        editor=preferences.edit();
    }

    /**
     * 获取sid
     * @return
     */
    public String getUserId(){
        return preferences.getString("sid","");
    }

    /**
     * 插入sid
     * @return
     */
    public void setUserId(String sid){
        editor.putString("sid",sid);
        editor.commit();
    }
}
